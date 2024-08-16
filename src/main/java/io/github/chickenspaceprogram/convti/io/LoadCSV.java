package io.github.chickenspaceprogram.convti.io;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

/**
 * Handles loading of a CSV file and conversion into a 1d array of strings.
 */
public class LoadCSV {
    private final int numRows;
    private final int numCols;
    private String[] data;
    public LoadCSV(String filename) throws IOException, CsvException, UserInputException {
        try (FileReader reader = new FileReader(filename)) {
            CSVReader csvReader = new CSVReader(reader);
            List<String[]> csvElements = csvReader.readAll();
            numRows = csvElements.size();
            numCols = csvElements.get(0).length;
            unevenRowCheck(csvElements, filename);
            createDataArray(csvElements, numRows, numCols);
            nonNumericEntryCheck(data, filename);
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
    private void unevenRowCheck(List<String[]> fileElements, String filename) throws UserInputException {
        int rowLength = fileElements.get(0).length;
        for (String[] rowInCSV : fileElements) {
            if (rowInCSV.length != rowLength) {
                throw new UserInputException(String.format("convti: The inputted file %s had rows of different lengths.", filename));
            }
        }
    }

    /**
     * Creates and returns a 1d array containing the data from the List<String[]> that CSVReader.readAll() provides.
     * @param inputList the list to be inputted
     * @param rows the number of rows in the inputted list
     * @param cols the number of columns in the inputted list
     * @return the array with the data from inputList
     */
    private void createDataArray(List<String[]> inputList, int rows, int cols) {
        data = new String[rows * cols];
        int currentRow = 0;
        for (String[] row : inputList) {
            System.arraycopy(row, 0, data, currentRow++ * cols, cols);
        }
    }

    /**
     * Checks to ensure that there are no non-numeric entries in the file.
     * @param entries the array containing the file's data
     * @param filename the filename containing the data (provided to make the thrown error more instructive)
     * @throws UserInputException when the file contains non-numeric entries
     */
    private void nonNumericEntryCheck(String[] entries, String filename) throws UserInputException {
        System.out.println(Arrays.toString(entries));
        for (String entry : entries) {
            if (!entry.matches("[0-9eE\\+.]+")) {
                throw new UserInputException(String.format("convti: Could not load file.\nThe entry `%s` in file `%s` had characters other than 0-9, e, E, +, and .", entry, filename));
            }
        }
    }
}