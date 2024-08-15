package io.github.chickenspaceprogram.convti;
import java.io.IOException;

import org.junit.Test;

import com.opencsv.exceptions.CsvException;

import io.github.chickenspaceprogram.convti.compiler.Word;
import io.github.chickenspaceprogram.convti.io.Input;
import io.github.chickenspaceprogram.convti.io.UserInputException;

// these unit tests are done the stupid way for convenience!
// I also don't reeeally know how to do proper unit tests, so yeah.

public class UnitTests {
    @Test
    public void allTests() throws IOException, CsvException, UserInputException {
        // put whatever you wanna test here
        wordTests();
        inputTests();
        TestLoadCSV yeet = new TestLoadCSV();
    }
    private static void wordTests() {
        // Rollover happens with bytes > 0x80, make note of this!
        Word argsWord = new Word((short) 0x1234);
        assert (argsWord.getLSB() == (byte) 0x34) : "Word (when given args): LSB incorrect, LSB value " + Byte.toString(argsWord.getLSB());
        assert (argsWord.getMSB() == (byte) 0x12) : "Word (when given args): MSB incorrect, MSB value " + Byte.toString(argsWord.getMSB());

        argsWord.setWord((short) 0xabcd);
        assert (argsWord.getLSB() == (byte) 0xcd) : "Word (when reset args together): LSB incorrect, LSB value " + Byte.toString(argsWord.getLSB());
        assert (argsWord.getMSB() == (byte) 0xab) : "Word (when reset args together): MSB incorrect, MSB value " + Byte.toString(argsWord.getMSB());
    }

    private static void inputTests() {
        // these unit tests by themselves are... inadequate. maybe make better
        String[] testInput1 = {"this is source", "this is output", "yvar"};

        Input input;
        try {
            input = new Input(testInput1);
            assert (input.getFormat().equals("yvar")) : "Input format incorrect.";
            assert (input.getInputFilename().equals("this is source")) : "Input filename incorrect.";
            assert (input.getOutputFilename().equals("this is output")) : "Input filename incorrect.";
        } catch (UserInputException e) {
            System.out.println(e.getMessage());
        }

    }

}