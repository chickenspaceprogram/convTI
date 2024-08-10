package io.github.chickenspaceprogram.convti;
// these unit tests are done the stupid way for convenience!
// I also don't reeeally know how to do proper unit tests, so yeah.
import java.io.IOException;

public class UnitTests {
    public static void main(String[] args) {
        // put whatever you wanna test here
        wordTests();
        inputTests();
        try {
            Input yeet = new Input(args);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    private static void wordTests() {
        System.out.println("Starting wordTests...");
        // Rollover happens with bytes > 0x80, make note of this!
        Word argsWord = new Word((short) 0x1234);
        if (argsWord.getLSB() != (byte) 0x34) {
            System.out.println("Word (when given args): LSB incorrect, LSB value " + Byte.toString(argsWord.getLSB()));
        }
        if (argsWord.getMSB() != (byte) 0x12) {
            System.out.println("Word (when given args): MSB incorrect, MSB value " + Byte.toString(argsWord.getMSB()));
        }
        Word emptyWord = new Word();
        if (emptyWord.getLSB() != (byte) 0x00) {
            System.out.println("Word (when not given args): LSB incorrect, LSB value " + Byte.toString(argsWord.getLSB()));
        }
        if (emptyWord.getMSB() != (byte) 0x00) {
            System.out.println("Word (when not given args): MSB incorrect, MSB value " + Byte.toString(argsWord.getMSB()));
        }
        emptyWord.setLSB((byte) 0x78);
        emptyWord.setMSB((byte) 0x90);
        if (emptyWord.getLSB() != (byte) 0x78) {
            System.out.println("Word (when reset args individually): LSB incorrect, LSB value " + Byte.toString(argsWord.getLSB()));
        }
        if (emptyWord.getMSB() != (byte) 0x90) {
            System.out.println("Word (when reset args individually): MSB incorrect, MSB value " + Byte.toString(argsWord.getMSB()));
        }
        argsWord.setWord((short) 0xabcd);
        if (argsWord.getLSB() != (byte) 0xcd) {
            System.out.println("Word (when reset args together): LSB incorrect, LSB value " + Byte.toString(argsWord.getLSB()));
        }
        if (argsWord.getMSB() != (byte) 0xab) {
            System.out.println("Word (when reset args together): MSB incorrect, MSB value " + Byte.toString(argsWord.getMSB()));
        }
    }

    private static void inputTests() {
        // these unit tests by themselves are... inadequate. maybe make better
        System.out.println("Starting inputTests...");
        String[] testInput1 = {"this is source", "this is output", "yvar"};

        Input input;
        try {
            input = new Input(testInput1);
            if (!input.getFormat().equals("yvar")) {
                System.out.println("Input format incorrect.");
            }
            if (!input.getInputFilename().equals("this is source")) {
                System.out.println("Input filename incorrect.");
            }
            if (!input.getOutputFilename().equals("this is output")) {
                System.out.println("Input filename incorrect.");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

}