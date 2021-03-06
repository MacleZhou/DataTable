/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools \\| Templates
 * and open the template in the editor.
 */
package bjakobitz.datatable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Brad
 */
public class DataTableTest {
    
    
    private File inputFile;
    DataTable<Double> instance;
    
    public DataTableTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws IOException, DataTableException {
        inputFile = new File("test/data/ci.out");
        instance = new DataTableImpl("Table","|");
        instance.setPrimaryKeyName("yr");
        instance.parseFile(inputFile);
        
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getTitle method, of class DataTable.
     */
    @Test
    public void testGetTitle() {
        System.out.println("getTitle");
        instance = new DataTableImpl("Title");
        String expResult = "Title";
        String result = instance.getTitle();
        assertEquals(expResult, result);
    }

    /**
     * Test of setTitle method, of class DataTable.
     */
    @Test
    public void testSetTitle() {
        System.out.println("setTitle");
        String title = "Title";
        instance = new DataTableImpl();
        instance.setTitle(title);
        String result = instance.getTitle();
        assertEquals(title,result);
    }

    /**
     * Test of getDelimiter method, of class DataTable.
     */
    @Test
    public void testGetDelimiter() {
        System.out.println("getDelimiter");
        instance = new DataTableImpl("Title","\\|");
        String expResult = "\\|";
        String result = instance.getDelimiter();
        assertEquals(expResult, result);
    }

    /**
     * Test of setDelimiter method, of class DataTable.
     */
    @Test
    public void testSetDelimiter() {
        System.out.println("setDelimiter");
        String delimiter = "\\|";
        instance = new DataTableImpl();
        instance.setDelimiter(delimiter);
        String result = instance.getDelimiter();
        assertEquals(delimiter,result);
    }

    /**
     * Test of getPrimaryKeyName method, of class DataTable.
     */
    @Test
    public void testGetPrimaryKeyName() {
        System.out.println("getPrimaryKeyName");
        String expResult = "yr";
        instance.setPrimaryKeyName(expResult);
        String result = instance.getPrimaryKeyName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getRowCount method, of class DataTable.
     */
    @Test
    public void testGetRowCount() throws IOException, DataTableException {
        System.out.println("getRowCount");
        int expResult = 100;
        int result = instance.getRowCount();
        assertEquals(expResult, result);
    }

    /**
     * Test of getColumnHeaders method, of class DataTable.
     */
    @Test
    public void testGetColumnHeaders() throws IOException, DataTableException {
        System.out.println("getColumnHeaders");
        List<String> expResult = new ArrayList();
        expResult.add("nrot_yrs");
        expResult.add("ncycles");
        expResult.add("yr");
        expResult.add("#events");
        expResult.add("yr_total");
        expResult.add("yrly_ave");
        expResult.add("Low_90.00%");
        expResult.add("High_90.00%");
        
        List<String> result = instance.getColumnHeaders();
        assertEquals(expResult, result);
    }

    /**
     * Test of setColumnHeaders method, of class DataTable.
     */
    @Test
    public void testSetColumnHeaders() {
        System.out.println("setColumnHeaders");
        List<String> headers = new ArrayList();
        headers.add("nrot_yrs");
        headers.add("ncycles");
        headers.add("yr");
        headers.add("#events");
        headers.add("yr_total");
        headers.add("yrly_ave");
        headers.add("Low_90.00%");
        headers.add("High_90.00%");
        instance = new DataTableImpl();
        instance.setColumnHeaders(headers);
        List<String> result = instance.getColumnHeaders();
        assertEquals(headers,result);
    }

    /**
     * Test of getRowStart method, of class DataTable.
     */
    @Test
    public void testGetRowStart() {
        System.out.println("getRowStart");
        instance = new DataTableImpl();
        instance.setRowStart(5);
        int expResult = 5;
        int result = instance.getRowStart();
        assertEquals(expResult, result);
    }

    /**
     * Test of parseFile method, of class DataTable.
     */
    @Test
    public void testParseFile() throws Exception {
        System.out.println("parseFile");
        instance = new DataTableImpl("Title","\\|");
        instance.parseFile(inputFile);
    }

    /**
     * Test of parseHeaders method, of class DataTable.
     */
    @Test
    public void testParseHeaders() throws IOException {
        System.out.println("parseHeaders");
        String headerLine = FileUtils.lineIterator(inputFile).nextLine();
        instance = new DataTableImpl("Title","|");
        instance.parseHeaders(headerLine);
        List<String> headers = new ArrayList();
        headers.add("nrot_yrs");
        headers.add("ncycles");
        headers.add("yr");
        headers.add("#events");
        headers.add("yr_total");
        headers.add("yrly_ave");
        headers.add("Low_90.00%");
        headers.add("High_90.00%");
        assertEquals(headers,instance.getColumnHeaders());
    }

    /**
     * Test of parseTableLines method, of class DataTable.
     */
    @Test
    public void testParseTableLines() throws IOException, DataTableException  {
        System.out.println("parseTableLines");
        String encoding = null;
        List<String> lines = FileUtils.readLines(inputFile, encoding);
        instance = new DataTableImpl("Title","\\|");
        instance.parseTableLines(lines);
    }

    /**
     * Test of getValue method, of class DataTable.
     */
    @Test
    public void testGetValue_int_int() throws IOException, DataTableException {
        System.out.println("getValue");
        int column = 1;
        int row = 17;
        Double expResult = 9.0;
        Object result = instance.getValue(column, row);
        assertEquals(expResult, result);
    }

    /**
     * Test of getValue method, of class DataTable.
     */
    @Test
    public void testGetValue_String_int() throws IOException, DataTableException {
        System.out.println("getValue");
        String columnName = "ncycles";
        String encoding = null;
        int row = 18;
        instance = new DataTableImpl("Title", "|");
        instance.parseTableLines(FileUtils.readLines(inputFile, encoding));
        Object expResult = 10.0;
        Object result = instance.getValue(columnName, row);
        assertEquals(expResult, result);
    }

    /**
     * Test of getValueByPrimaryKey method, of class DataTable.
     */
    @Test
    public void testGetValueByPrimaryKey() throws Exception {
        System.out.println("getValueByPrimaryKey");
        String columnName = "#events";
        Double rowValue = 10.0;
        instance.setPrimaryKeyName("yr");
        Object expResult = 5.00;
        Object result = instance.getValueByPrimaryKey(columnName, rowValue);
        assertEquals(expResult, result);
    }

    /**
     * Test of getRow method, of class DataTable.
     */
    @Test
    public void testGetRow() throws IOException, DataTableException {
        System.out.println("getRow");
        String encoding = null;
        int rowIndex = 13;
        instance = new DataTableImpl("Title", "|");
        instance.parseTableLines(FileUtils.readLines(inputFile, encoding));
        List<Double> expResult = new ArrayList();
        expResult.add(2.0);
        expResult.add(7.0);
        expResult.add(14.0);
        expResult.add(1.00);
        expResult.add(0.40994);
        expResult.add(3.90917);
        expResult.add(2.25758);
        expResult.add(79.425);
        List result = instance.getRow(rowIndex);
        assertEquals(expResult, result);
    }

    /**
     * Test of getRowByPrimaryKey method, of class DataTable.
     */
    @Test
    public void testGetRowByPrimaryKey() throws Exception {
        System.out.println("getRowByPrimaryKey");
        Double rowValue = 14.0;
        instance.setPrimaryKeyName("yr");
        List<Double> expResult = new ArrayList();
        expResult.add(2.0);
        expResult.add(7.0);
        expResult.add(14.0);
        expResult.add(1.00);
        expResult.add(0.40994);
        expResult.add(3.90917);
        expResult.add(2.25758);
        expResult.add(79.425);
        instance.addRow(expResult);
        List result = instance.getRowByPrimaryKey(rowValue);
        assertEquals(expResult, result);
    }

    /**
     * Test of addValue method, of class DataTable.
     */
    @Test
    public void testAddValue_GenericType_int() throws IOException, DataTableException {
        System.out.println("addValue");
        Double value = 51.0;
        int column = 2;
        instance.addValue(value, column);
        Object result = instance.getValue(2, instance.rowCount-1);
        assertEquals(value,result);
    }

    /**
     * Test of addValue method, of class DataTable.
     */
    @Test
    public void testAddValue_GenericType_String() throws IOException, DataTableException {
        System.out.println("addValue");
        Double value = 51.0;
        String columnName = "ncycles";
        instance.addValue(value, columnName);
        Object result = instance.getValue(columnName, instance.rowCount-1);
        assertEquals(value,result);
    }

    /**
     * Test of addRow method, of class DataTable.
     */
    @Test
    public void testAddRow() throws Exception {
        System.out.println("addRow");
        List expResult = new ArrayList();
        expResult.add(2);
        expResult.add(7);
        expResult.add(14);
        expResult.add(1.00);
        expResult.add(0.40994);
        expResult.add(3.90917);
        expResult.add(2.25758);
        expResult.add(79.425);
        
        instance.addRow(expResult);
        List result = instance.getRow(instance.rowCount - 1);
        
        assertEquals(expResult,result);
    }

    /**
     * Test of removeRow method, of class DataTable.
     */
    @Test
    public void testRemoveRow_int() {
        System.out.println("removeRow");
        int row = 3;
        List oldRow = instance.getRow(row);
        instance.removeRow(row);
        List newRow = instance.getRow(row);
        
        assertNotEquals(oldRow,newRow);
    }

    /**
     * Test of removeRow method, of class DataTable.
     */
    @Test
    public void testRemoveRow_updatedIndices_GenericType() throws DataTableException {
        System.out.println("removeRow_reindex");
        instance.setPrimaryKeyName("yr");
        Double rowValue = 15.0;
        Double newValue = 20.0;
        List oldRow = instance.getRowByPrimaryKey(rowValue);
        List newCopy = instance.getRowByPrimaryKey(newValue);
        instance.removeRow(rowValue);
        try {
            List newRow = instance.getRowByPrimaryKey(newValue);
            assertEquals(newCopy,newRow);
        } catch (DataTableException ex) {
            
        }
    }
    
    @Test
    public void testRemoveRow_removed_GenericType() throws DataTableException{
        System.out.println("removeRow_remove");
        instance.setPrimaryKeyName("yr");
        Double rowValue = 15.0;
        List oldRow = instance.getRowByPrimaryKey(rowValue);
        instance.removeRow(rowValue);
        try {
            List newRow = instance.getRowByPrimaryKey(rowValue);
            fail("Did not throw an exception.");
        } catch (DataTableException ex) {
            
        }
    }

    /**
     * Test of insertValue method, of class DataTable.
     */
    @Test
    public void testInsertValue_3args_1() {
        System.out.println("insertValue");
        Double value = 51.0;
        int column = 2;
        int row = 10;
        instance.insertValue(value, column, row);
        Object result = instance.getValue(column, row);
        
        assertEquals(value,result);
    }

    /**
     * Test of insertValue method, of class DataTable.
     */
    @Test
    public void testInsertValue_3args_2() {
        System.out.println("insertValue");
        Double value = 51.0;
        String columnName = "yr";
        int row = 10;
        instance.insertValue(value, columnName, row);
        Object result = instance.getValue(columnName, row);
        
        assertEquals(value,result);
    }

    /**
     * Test of insertValueByPrimaryKey method, of class DataTable.
     */
    @Test
    public void testInsertValueByPrimaryKey() throws Exception {
        System.out.println("insertValueByPrimaryKey");
        Double value = 5.0;
        String columnName = "nrot_yrs";
        
        Double rowValue = 20.0;

        instance.insertValueByPrimaryKey(value, columnName, rowValue);
        Object result = instance.getValueByPrimaryKey(columnName, rowValue);
        
        assertEquals(value,result);
    }

    /**
     * Test of writeFile method, of class DataTable.
     */
    @Test
    public void testWriteFile_File_int() throws Exception {
        System.out.println("writeFile");
        File outputFile = new File("test_output.txt");
        int startingRow = 0;
        if(outputFile.exists())
            outputFile.delete();
        writeHeaders(outputFile);
        instance.writeFile(outputFile, startingRow);
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * Test of writeFile method, of class DataTable.
     */
    @Test
    public void testWriteFile_File() throws Exception {
        System.out.println("writeFile");
        File outputFile = new File("output.out");
        if(outputFile.exists())
            outputFile.delete();
        writeHeaders(outputFile);
        instance.writeFile(outputFile);
        System.err.print("Remember to diff the output file.");
        
    }
    
    private void writeHeaders(File outputFile) throws IOException{
        String encoding = null;
        String line = "";
        for(int i=0;i<instance.getColumnHeaders().size();i++){
            line += instance.getColumnHeaders().get(i) +  " " + instance.delimiter + " ";
            
        }
        FileUtils.writeStringToFile(outputFile, line + System.lineSeparator(), encoding, true);
    }

    public class DataTableImpl extends DataTable<Double> {

        public DataTableImpl(){
            super();
        }
        
        public DataTableImpl(String title){
            super(title);
        }
        
        public DataTableImpl(String title,String delimiter){
            super(title,delimiter);
        }
        
        @Override
        public void parseRow(String line) throws DataTableException {
            
            ArrayList<Double> row;
            String[] lineColumns;
            lineColumns = line.trim().split(Pattern.quote(delimiter));
            row = new ArrayList();
            for (String lineColumn : lineColumns) {
                try{
                    row.add(Double.parseDouble(lineColumn));
                }catch(NullPointerException | NumberFormatException ex){
                    row.add(Double.NaN);
                }
            }
                addFullRow(row);
                addRow(row);
            }
        
        private void addFullRow(ArrayList<Double> row) {
            if (row.size() < columnHeaders.size()) {
                for (int i = row.size(); i < columnHeaders.size(); i++) {
                    row.add(Double.NaN);
                }
            }
        }
    }
    
}
