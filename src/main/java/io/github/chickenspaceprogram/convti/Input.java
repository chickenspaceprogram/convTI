package io.github.chickenspaceprogram.convti;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

public class Input {
    // two maps are here since we want to ensure that if -f is entered that the user can't also enter --format and specify two different formats.
    private final Map<String, String> OPTIONS_SHORT = Map.of(
        "-v", "--version",
        "-h", "--help"
    );
    private final Map<String, String> OPTIONS_LONG = Map.of(
        "--version", "-v",
        "--help", "-h"
    );
    private LinkedHashMap<String, String> allFormats = new LinkedHashMap<>();
    private String format;
    private String inputFilename;
    private String outputFilename;
    private int numFileArgs = 0;

    public Input(String[] arguments) throws UserInputException {
        // Takes user's input from command line and stores the relevant parts to formatAs, inputFilename, and outputFilename
        addFormats();
        validateOptions(arguments);
        boolean isAnOption;
        if (arguments.length < 3) {
            throw new UserInputException("convti: not enough arguments given; try --help if you need it");
        }
        for (String arg : arguments) {
            isAnOption = OPTIONS_SHORT.containsKey(arg) || OPTIONS_LONG.containsKey(arg);
            if (!isAnOption) {
                getReqdArgs(arg);
            } else {
                // replace this with actual option-handling code
                System.out.println("Something's gone wrong.");
                System.exit(-1);
            }
        }
        validateFormat();
    }

    public String getFormat() {
        return format;
    }

    public String getInputFilename() {
        return inputFilename;
    }

    public String getOutputFilename() {
        return outputFilename;
    }

    private void addFormats() {
        // there is absolutely a better way to do this, but stupid is easy
        allFormats.put("prgm", "program");
        allFormats.put("elprgm", "edit-locked program");
        allFormats.put("rlist", "real list");
        allFormats.put("clist", "complex list");
        allFormats.put("matrix", "matrix");
        allFormats.put("string", "string");
        allFormats.put("rnum", "real number");
        allFormats.put("cnum", "complex number");
        allFormats.put("yvar", "y-variable");
        allFormats.put("gdb", "GDB");
        allFormats.put("pic", "Pic");
        allFormats.put("tbl", "table settings");
    }

    private void validateFormat() throws UserInputException {
        if (!allFormats.containsKey(format)) {
            throw new UserInputException("Invalid format entered. Enter `convti --help` to see a list of valid formats.");
        }
    }

    private void validateOptions(String[] arguments) throws UserInputException {
        HashSet<String> optionsFound = new HashSet<>();
        for (String arg : arguments) {
            switch (arg) {
                case "-h", "--help" -> printHelp();
                case "-v", "--version" -> printVersion();
            }
            if (optionsFound.contains(arg)) {
                throw new UserInputException("convti: optional argument was listed twice.");
            }
            boolean isInvalidOption = arg.contains("-") && !OPTIONS_SHORT.containsKey(arg) && !OPTIONS_LONG.containsKey(arg);
            if (isInvalidOption) {
                throw new UserInputException("convti: invalid optional argument given; enter `convti --help` to see a list of valid arguments.");
            }
            if (arg.contains("-")) {
                // don't know why I did it like this but hey, it works!
                optionsFound.add(arg);
                if (arg.contains("--")) {
                    optionsFound.add(OPTIONS_LONG.get(arg));
                } else {
                    optionsFound.add(OPTIONS_SHORT.get(arg));
                }
            }
        }
    }

    private void getReqdArgs(String arg) throws UserInputException {
        // this is probably a bad way to do this, but it's fiiiiiine
        switch (numFileArgs) {
            case 0 -> {
                ++numFileArgs;
                inputFilename = arg;
            }
            case 1 -> {
                ++numFileArgs;
                outputFilename = arg;
            }
            case 2 -> {
                ++numFileArgs;
                format = arg;
            }
            default -> {
                throw new UserInputException("convti: too many arguments given");
            }
        }
    }

    private void printHelp() {
        System.out.println("usage: convti [<options>] <source> <filename> <format>\n");
        System.out.println("<source>\t\tname of source file to compile");
        System.out.println("<filename>\t\tdesired filename on calculator");
        System.out.println("\noptions:");
        System.out.println("-h, --help\t\tprints this message and quits");
        System.out.println("-v. --version\t\tprints version and quits");
        System.out.println("\nValid formats:");
        for (String key : allFormats.keySet()) {
            System.out.println(key + " : " + allFormats.get(key));
        }
        System.exit(0);
    }

    private void printVersion() {
        System.out.println("convti version 0.3.0 (alpha)");
        System.exit(0);
    }
}