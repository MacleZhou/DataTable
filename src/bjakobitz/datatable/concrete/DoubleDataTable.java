/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bjakobitz.datatable.concrete;

import bjakobitz.datatable.DataTable;
import bjakobitz.datatable.DataTableException;
import java.util.ArrayList;

/**
 *
 * @author Brad
 */
public class DoubleDataTable extends DataTable<Double>{

    /**
     * Creates a Double table
     * delimiter is defaulted to a space
     * rowStart is defaulted to 1 so is skips over the headers
     */
    public DoubleDataTable() {
        super();
    }

    /**
     * Creates a Double table with the given title
     * delimiter is defaulted to space
     * rowStart is defaulted to 1 so is skips over the headers
     * @param title The title of the table
     */
    public DoubleDataTable(String title) {
        super(title);
    }

    /**
     * Creates a Double table with the given title
     * delimiter is set to the passed in delimiter
     * rowStart is defaulted to 1 so is skips over the headers
     * @param title The title of the table
     * @param delimiter The delimeter used to parse the headers and rows into columns
     */
    public DoubleDataTable(String title, String delimiter) {
        super(title,delimiter);
    }
    
    /**
     * Parses a row given by parseFile or parseLines using the delimiter and puts the values in the table 2D array
     * @param line the line to parse into columns
     * @throws DataTableException 
     */
    @Override
    protected void parseRow(String line) throws DataTableException {
        ArrayList<Double> row;
        String[] lineColumns;
        lineColumns = line.split(delimiter);
        row = new ArrayList();
        for (String lineColumn : lineColumns) {
            row.add(Double.parseDouble(lineColumn));
        }
        addRow(row);
    }
    
}
