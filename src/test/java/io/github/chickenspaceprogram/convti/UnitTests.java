package io.github.chickenspaceprogram.convti;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import io.github.chickenspaceprogram.convti.io.Input;
import io.github.chickenspaceprogram.convti.io.UserInputException;
import io.github.chickenspaceprogram.convti.converter.Word;



import org.junit.Test;

// these unit tests are done the stupid way for convenience!
// I also don't reeeally know how to do proper unit tests, so yeah.

public class UnitTests {
    @Test
    public void allTests() {
        // put whatever you wanna test here
        wordTests();
        inputTests();
        String thing = "\" this is a test yo\ndoes this get highlighted?\n\"what about this \"";
        Pattern regex = Pattern.compile("( +(?=.*(?:\"|$|->))(?!\".*))");
        Matcher matcher = regex.matcher(thing);
        if (matcher.matches()) {
            System.out.println(matcher.group(1));
        }
        assert (matcher.matches()) : "Matcher did not match";
    }
    private static void wordTests() {
        // Rollover happens with bytes > 0x80, make note of this!
        Word argsWord = new Word((short) 0x1234);
        assert (argsWord.getLSB() == (byte) 0x34) : "Word (when given args): LSB incorrect, LSB value " + Byte.toString(argsWord.getLSB());
        assert (argsWord.getMSB() == (byte) 0x12) : "Word (when given args): MSB incorrect, MSB value " + Byte.toString(argsWord.getMSB());

        Word emptyWord = new Word();
        assert (emptyWord.getLSB() == (byte) 0x00) : "Word (when not given args): LSB incorrect, LSB value " + Byte.toString(argsWord.getLSB());
        assert (emptyWord.getMSB() == (byte) 0x00) : "Word (when not given args): MSB incorrect, MSB value " + Byte.toString(argsWord.getMSB());
        
        emptyWord.setLSB((byte) 0x78);
        emptyWord.setMSB((byte) 0x90);
        assert (emptyWord.getLSB() == (byte) 0x78) : "Word (when reset args individually): LSB incorrect, LSB value " + Byte.toString(argsWord.getLSB());
        assert (emptyWord.getMSB() == (byte) 0x90) : "Word (when reset args individually): MSB incorrect, MSB value " + Byte.toString(argsWord.getMSB());

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