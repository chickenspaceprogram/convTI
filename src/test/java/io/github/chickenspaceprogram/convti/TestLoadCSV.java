package io.github.chickenspaceprogram.convti;
import java.io.IOException;

import com.opencsv.exceptions.CsvException;

import io.github.chickenspaceprogram.convti.io.LoadCSV;
import io.github.chickenspaceprogram.convti.io.UserInputException;

public class TestLoadCSV {
    public TestLoadCSV() throws IOException, CsvException, UserInputException {
        String filename = "testcsv.csv";
        LoadCSV csv = new LoadCSV(filename);
        assert (csv.getNumCols() == 4) : "Incorrect number of columns (" + csv.getNumCols() + ") read.";
        assert (csv.getNumRows() == 3) : "Incorrect number of rows (" + csv.getNumRows() + ") read.";
    }
}