/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bjakobitz.datatable.concrete;

import bjakobitz.datatable.DataTable;
import bjakobitz.datatable.DataTableException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Brad
 */
public class StringDataTable extends DataTable<String>{

    /**
     * Creates a generic table
     * delimiter is defaulted to a space
     * rowStart is defaulted to 1 so is skips over the headers
     */
    public StringDataTable() {
        super();
    }

    /**
     * Creates a generic table with the given title
     * delimiter is defaulted to space
     * rowStart is defaulted to 1 so is skips over the headers
     * @param title The title of the table
     */
    public StringDataTable(String title) {
        super(title);
    }

    /**
     * Creates a generic table with the given title
     * delimiter is set to the passed in delimiter
     * rowStart is defaulted to 1 so is skips over the headers
     * @param title The title of the table
     * @param delimiter The delimeter used to parse the headers and rows into columns
     */
    public StringDataTable(String title, String delimiter) {
        super(title,delimiter);
    }

     /**
     * Parses a row given by parseFile or parseLines using the delimiter and puts the values in the table 2D array
     * @param line the line to parse into columns
     * @throws DataTableException 
     */
    @Override
    protected void parseRow(String line) throws DataTableException {
        ArrayList<String> row;
        String[] lineColumns;
        lineColumns = line.split(delimiter);
        row = new ArrayList(Arrays.asList(lineColumns));
        addRow(row);
    }
}
