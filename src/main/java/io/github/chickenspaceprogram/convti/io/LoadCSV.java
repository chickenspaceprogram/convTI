package io.github.chickenspaceprogram.convti.io;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

public class LoadCSV {
    private final int numRows;
    private final int numCols;
    private final String[] data;
    private final String filename;
    public LoadCSV(String filename) throws IOException, CsvException, UserInputException {
        this.filename = filename;
        int currentRow = 0;

        try (FileReader reader = new FileReader(filename)) {
            CSVReader csvReader = new CSVReader(reader);
            List<String[]> csvElements = csvReader.readAll();
            numRows = csvElements.size();
            numCols = csvElements.get(0).length;
            unevenRowCheck(csvElements);
            data = new String[numRows * numCols];
            for (String[] row : csvElements) {
                System.arraycopy(row, 0, data, currentRow * numCols, numCols);
                ++currentRow;
            }
        }
    }

    /**
     * Returns the number of rows in this CSV.
     * @return the number of rows
     */
    public int getNumRows() {
        return numRows;
    }

    /**
     * Returns the number of columns in this CSV.<br>
     * If this CSV is a list, then this is equal to the length of the list.
     * @return the number of columns
     */
    public int getNumCols() {
        return numCols;
    }

    /**
     * Returns the data from the file.<br>
     * The data is always formatted as a 1D array, since that is how data is represented on the TI-8x series.
     * @return the data from the file
     */
    public String[] getData() {
        return data;
    }

    /**
     * Ensures that all rows have the same length; if they don't, UserInputException is thrown.
     * 
     * @param fileElements the elements from a CSV file
     * @throws UserInputException if the rows do not have the same length
     */
    private void unevenRowCheck(List<String[]> fileElements) throws UserInputException {
        int rowLength = fileElements.get(0).length;
        for (String[] rowInCSV : fileElements) {
            if (rowInCSV.length != rowLength) {
                throw new UserInputException(String.format("The inputted file %s had rows of different lengths.", filename));
            }
        }
    }
}