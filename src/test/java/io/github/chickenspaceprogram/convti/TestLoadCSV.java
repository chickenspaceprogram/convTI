package io.github.chickenspaceprogram.convti;
import java.io.IOException;
import java.util.Arrays;

import com.opencsv.exceptions.CsvException;

import io.github.chickenspaceprogram.convti.io.LoadCSV;
import io.github.chickenspaceprogram.convti.io.UserInputException;

public class TestLoadCSV {
    public TestLoadCSV() throws IOException, CsvException, UserInputException {
        LoadCSV csv = new LoadCSV("testcsv.csv");
        String[] expectedResult = {"123", "4.12356E+123", "789", "123", "147", "258", "369", "100", "901", "801", "245", "676"};
        assert (csv.getNumCols() == 4) : "Incorrect number of columns (" + csv.getNumCols() + ") read.";
        assert (csv.getNumRows() == 3) : "Incorrect number of rows (" + csv.getNumRows() + ") read.";
        assert (Arrays.equals(csv.getData(), expectedResult)) : "CSV did not convert correctly.";

        // this code is pretty cursed, sorry
        try {
            LoadCSV notSameLength = new LoadCSV("badlength.csv");
            throw new Error("No exception thrown when CSV has rows of different lengths.");
        } catch (UserInputException e) {
            assert (e.getMessage().equals("convti: The inputted file badlength.csv had rows of different lengths.")) : "Incorrect error message thrown.";
        }

        try {
            LoadCSV badChars = new LoadCSV("badchars.csv");
            throw new Error("No exception thrown when CSV has non-numeric characters.");
        } catch (UserInputException e) {
            assert (e.getMessage().equals("convti: Could not load file.\nThe entry `36test9` in file `badchars.csv` had characters other than 0-9, e, E, +, and .")) : "Incorrect error message thrown.";
        }
    }
}