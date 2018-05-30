/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bjakobitz.datatable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

/**
 *
 * @author Brad
 * @param <T> The class type held in this table.
 */
public abstract class DataTable<T> {

    protected String title;
    protected String delimiter;
    /**
     * The row to start parsing rows at in the file or line array.
     */
    protected int rowStart;
    protected int rowCount;
    protected final List<List<T>> columns;
    protected final Map<String, Integer> columnIndices;
    protected final List<String> columnHeaders;
    protected final Map<T, Integer> primeKeyIndices;
    protected String primaryKeyName;

    /**
     * Creates a generic table
     * delimiter is defaulted to a space
     * rowStart is defaulted to 1 so is skips over the headers
     */
    public DataTable() {
        title = "Table title";
        delimiter = " ";
        columns = new ArrayList();
        columnIndices = new HashMap();
        primeKeyIndices = new HashMap();
        columnHeaders = new ArrayList();
        rowStart = 1;
        rowCount = 0;
    }

    /**
     * Creates a generic table with the given title
     * delimiter is defaulted to space
     * rowStart is defaulted to 1 so is skips over the headers
     * @param title The title of the table
     */
    public DataTable(String title) {
        this.title = title;
        delimiter = " ";
        columns = new ArrayList();
        columnIndices = new HashMap();
        primeKeyIndices = new HashMap();
        columnHeaders = new ArrayList();
        rowStart = 1;
        rowCount = 0;
    }

    /**
     * Creates a generic table with the given title
     * delimiter is set to the passed in delimiter
     * rowStart is defaulted to 1 so is skips over the headers
     * @param title The title of the table
     * @param delimiter The delimeter used to parse the headers and rows into columns
     */
    public DataTable(String title, String delimiter) {
        this.title = title;
        this.delimiter = delimiter;
        columns = new ArrayList();
        columnIndices = new HashMap();
        primeKeyIndices = new HashMap();
        columnHeaders = new ArrayList();
        rowStart = 1;
        rowCount = 0;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    /**
     * Gets the primary key column name for searching row by value.
     * @return Primary key column name.
     */
    public String getPrimaryKeyName() {
        return primaryKeyName;
    }

    public int getRowCount() {
        return rowCount;
    }
    
    /**
     * Gives a deep copy of the column headers
     * @return copy of column headers
     */
    public List<String> getColumnHeaders() {
        ArrayList<String> headerCopy = new ArrayList();
        
        columnHeaders.forEach((header) -> {
            headerCopy.add(header);
        });
        return headerCopy;
    }
    
    /**
     * This method is meant to initialize your table.
     * It will clear any data you previously had and start a new table.
     * @param headers Header names.
     */
    public final void setColumnHeaders(List<String> headers){
        columnHeaders.clear();
        columnIndices.clear();
        columns.clear();
        for(int i=0;i<headers.size();i++){
            columnHeaders.add(headers.get(i));
            columnIndices.put(headers.get(i), i);
            columns.add(new ArrayList());
        }
    }
    
    /**
     * Sets the primary key column name for searching row by value.
     * @param primaryKeyName The column name you want for your primary key lookup.
     */
    public void setPrimaryKeyName(String primaryKeyName) {
        this.primaryKeyName = primaryKeyName;
    }

    /**
     * Gets the line in the file or lines array to start parsing row data.
     * @return the starting row index.
     */
    public int getRowStart() {
        return rowStart;
    }

    /**
     * Sets the line in the file or lines array to start creating rows.
     * @param rowStart The line you want to start parsing at.
     */
    public void setRowStart(int rowStart) {
        this.rowStart = rowStart;
    }

    /**
     * This is to be used if the file contains one table
     * This calls parseHeaders and parseRow - I would advise overriding these methods instead of parseFile
     * because it handles some data structure setup and catches bounds.
     * @param inputFile The file to read the table from.
     * @throws IOException
     * @throws DataTableException - Throws if the column count in a row does not match the table's column count.
     */
    public void parseFile(File inputFile) throws IOException, DataTableException {
        LineIterator it;

        it = FileUtils.lineIterator(inputFile);

        try {

            // add the headers
            if (it.hasNext()) {
                parseHeaders(it.nextLine());
            }

            setupColumns();

            // add the rows
            while (it.hasNext()) {
                parseRow(it.nextLine());
            }
        } finally {
            it.close();
        }
    }

    /**
     * Parses the header line given using the delimiter and stores the headers in the headers array and lookup.
     * Should be overridden instead of parseFile.
     * @param headerLine The line containing the column headers.
     */
    protected void parseHeaders(String headerLine){
        String[] lineColumns;
        lineColumns = headerLine.split(delimiter);
        for (int i = 0; i < lineColumns.length; i++) {
            columnIndices.put(lineColumns[i], i);
            columnHeaders.add(lineColumns[i]);
        }
    }

    /**
     * Parses a row given by parseFile or parseLines using the delimiter and puts the values in the table 2D array
     * @param line the line to parse into columns
     * @throws DataTableException 
     */
    protected abstract void parseRow(String line) throws DataTableException;

    /**
     * Initializes the 2D data structure based on the column headers.
     */
    protected final void setupColumns() {
        for (int i = 0; i < columnHeaders.size(); i++) {
            columns.add(new ArrayList());
        }
    }

    /**
     * Parses a table give an array of table lines
     * This calls parseHeaders and parseRow - I would advise overriding these methods instead of parseTableLines
     * because it handles some data structure setup and catches bounds.
     * @param lines lines of text table to be parsed.
     * @throws DataTableException 
     */
    public void parseTableLines(List<String> lines) throws DataTableException {
        String line;

        // add the headers
        if (lines.size() > 0) {
            line = lines.get(0);
            parseHeaders(line);
        }

        setupColumns();

        // add the rows
        for (int i = rowStart; i < lines.size(); i++) {
            line = lines.get(i);
            parseRow(line);
        }
    }

    /**
     * Gets the row index of a given primary key value.
     * @param rowValue A primary key value.
     * @return The index of the row containing the primary key value.
     * @throws DataTableException - Throws an exception if the primary key column is not set or the primary key value could not be found.
     */
    private int getPrimaryKeyIndex(T rowValue) throws DataTableException {
        int row;

        if (primaryKeyName == null) {
            throw new DataTableException("No primary key set.");
        }

        if (primeKeyIndices.containsKey(rowValue)) {
            row = primeKeyIndices.get(rowValue);
        } else {
            throw new DataTableException("Row value could not be found in primary column.");
        }

        return row;
    }

    /**
     * Gets the value at a given column and row.
     * @param column column index
     * @param row row index
     * @return 
     */
    public T getValue(int column, int row) {
        return columns.get(column).get(row);
    }

    /**
     * Gets the value given a column name and row index.
     * @param columnName The header name for a column
     * @param row The index of the row.
     * @return 
     */
    public T getValue(String columnName, int row) {
        int column = columnIndices.get(columnName);

        return getValue(column, row);
    }

    /**
     * Gets the value given a column name and primary key value.
     * @param columnName The column header name
     * @param rowValue The primary key value
     * @return
     * @throws DataTableException - Throws an exception if the primary key column is not set or the primary key value could not be found.
     */
    public T getValueByPrimaryKey(String columnName, T rowValue) throws DataTableException {
        return getValue(columnName, getPrimaryKeyIndex(rowValue));
    }

    /**
     * Gets a row of values given a row index.
     * @param rowIndex row index of the row you want.
     * @return 
     */
    public List<T> getRow(int rowIndex) {
        ArrayList<T> row = new ArrayList();

        for (int i = 0; i < columnHeaders.size(); i++) {
            row.add(columns.get(i).get(rowIndex));
        }
        return row;
    }

    /**
     * Gets a row of values given a primary key value.
     * @param rowValue The primary key value.
     * @return
     * @throws DataTableException - Throws an exception if the primary key column is not set or the primary key value could not be found.
     */
    public List<T> getRowByPrimaryKey(T rowValue) throws DataTableException {
        return getRow(getPrimaryKeyIndex(rowValue));
    }

    /**
     * This method adds the value to the given column index.
     * It adds null values to the other columns to keep the columns in sync.
     * I don't expect this method to be used much.
     * @param value The value to add to the table
     * @param column The column index to add the value to.
     */
    public void addValue(T value, int column) {
        for (int i = 0; i < columnHeaders.size(); i++) {
            if (i == column) {
                columns.get(i).add(value);
            } else {
                columns.get(i).add(null);
            }
            if (columnHeaders.get(i).equals(primaryKeyName)) {
                primeKeyIndices.put(value, i);
            }
        }
        rowCount++;
    }

    /**
     * Adds the value to the given column matching the columnName
     *
     * @param value The value to be set.
     * @param columnName The name of the column you want to add the value to.
     */
    public void addValue(T value, String columnName) {
        int column = columnIndices.get(columnName);

        addValue(value, column);
    }

    /**
     * Adds a row of data to all the columns
     * Adds a primary key lookup value.
     * @param row The row of values to add
     * @throws DataTableException - Throws if the row's column count doesn't match the header's count.
     */
    public void addRow(List<T> row) throws DataTableException {

        if (row.size() != columnHeaders.size()) {
            throw new DataTableException("The row size does not match the table column count.");
        }
        for (int i = 0; i < columns.size(); i++) {
            columns.get(i).add(row.get(i));
            if (columnHeaders.get(i).equals(primaryKeyName)) {
                primeKeyIndices.put(row.get(i), i);
            }
        }
        rowCount++;
    }

    /**
     * Removes a row at a specific index
     * Removes the primary key index if there is one.
     * @param row The row index to remove.
     */
    public void removeRow(int row) {
        for (int i = 0; i < columns.size(); i++) {
            if (i < columns.get(i).size()) {
                if (columnHeaders.get(i).equals(primaryKeyName)) // remove the primary key index first before removing the row.
                {
                    primeKeyIndices.remove(columns.get(i).get(row), i);
                }

                columns.get(i).remove(row);
                rowCount--;
            }
        }
    }

    /**
     * Removes a row based on the primary key value.
     * @param rowValue The primary key value
     * @throws DataTableException - Throws an exception if the primary key column is not set or the primary key value could not be found.
     */
    public void removeRow(T rowValue) throws DataTableException {
        removeRow(getPrimaryKeyIndex(rowValue));
    }

    /**
     * Inserts a value at a given column and row
     * @param value The value to be inserted
     * @param column The column index.
     * @param row The row index.
     */
    public void insertValue(T value, int column, int row) {
        columns.get(column).set(row, value);
        if (columnHeaders.get(column).equals(primaryKeyName)) {
            primeKeyIndices.put(value, row);
        }
    }

    /**
     * Inserts a value at a given column and row
     * @param value The value to be inserted
     * @param columnName The header name for a column
     * @param row The row index
     */
    public void insertValue(T value, String columnName, int row) {
        int column = columnIndices.get(columnName);

        insertValue(value, column, row);
    }

    /**
     * Inserts a value at a given column and primary key value
     * @param value The value to be inserted
     * @param columnName The header name for a column
     * @param rowValue The row that matches a primary key value
     * @throws DataTableException - Throws an exception if the primary key column is not set or the primary key value could not be found.
     */
    public void insertValueByPrimaryKey(T value, String columnName, T rowValue) throws DataTableException {
        int row;

        if (primaryKeyName == null) {
            throw new DataTableException("No primary key set.");
        }

        if (primeKeyIndices.containsKey(rowValue)) {
            row = primeKeyIndices.get(rowValue);
        } else {
            throw new DataTableException("Row name could not be found in table.");
        }

        insertValue(value, columnName, row);
    }

    /**
     * Writes the rows of the table starting at the starting row
     * @param outputFile The file to write to. This will append the row to the file.
     * @param startingRow
     * @throws IOException 
     */
    public void writeFile(File outputFile, int startingRow) throws IOException {
        String encoding = null;
        String line;

        for (int i = startingRow; i < columns.get(0).size(); i++) {
            line = "";
            for (int j = 0; j < columnHeaders.size(); j++) {
                line += getValue(j, i);
                if (j < columnHeaders.size() - 1) {
                    line += delimiter;
                }
            }
            FileUtils.writeStringToFile(outputFile, line + System.lineSeparator(), encoding, true);
        }
    }

    /**
     * Writes all the rows of the table
     * I would suggest overriding this method to right the headers and rows the way you want them written.
     * @param outputFile The file to write to. This will append the row to the file.
     * @throws IOException 
     */
    public void writeFile(File outputFile) throws IOException {
        writeFile(outputFile, 0);
    }
}
