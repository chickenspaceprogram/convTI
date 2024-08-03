import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

public class Input {

    private final Map<String, String> OPTIONS_SHORT = Map.of(
        "-f", "--format",
        "-v", "--version",
        "-h", "--help"
    );
    private final Map<String, String> OPTIONS_LONG = Map.of(
        "--format", "-f",
        "--version", "-v",
        "--help", "-h"
    );
    private final HashSet<String> FORMATS = new HashSet<>(Arrays.asList("rlist", "clist", "matrix", "string"));
    private String formatAs;
    private String inputFilename;
    private String outputFilename;
    private int numFileArgs = 0;

    public void getInput(String[] arguments) throws IOException {
        // Takes user's input from command line and stores the relevant parts to formatAs, inputFilename, and outputFilename

        validateOptions(arguments);
        boolean isAnOption;

        for (int i = 0; i < arguments.length; ++i) {
            isAnOption = OPTIONS_SHORT.containsKey(arguments[i]) || OPTIONS_LONG.containsKey(arguments[i]);
            if (isAnOption) {
                switch (arguments[i]) {
                    case "-f", "--format" -> {
                        ++i;
                        catchFormattingError(arguments, i);
                        formatAs = arguments[i];
                    }
                }
            } else {
                storeFilename(arguments[i]);
            }
        }
        if (numFileArgs < 2) {
            throw new IOException("convti: not enough arguments given; try --help if you need it");
        }
    }

    private void validateOptions(String[] arguments) throws IOException {
        HashSet<String> optionsFound = new HashSet<>();
        for (String arg : arguments) {
            switch (arg) {
                case "-h", "--help" -> printHelp();
                case "-v", "--version" -> printVersion();
            }
            if (optionsFound.contains(arg)) {
                throw new IOException("convti: optional argument was listed twice.");
            }
            boolean isInvalidOption = arg.contains("-") && !OPTIONS_SHORT.containsKey(arg) && !OPTIONS_LONG.containsKey(arg);
            if (isInvalidOption) {
                throw new IOException("convti: invalid optional argument given; enter `convti --help` to see a list of valid arguments.");
            }
            if (arg.contains("-")) {
                optionsFound.add(arg);
                if (arg.contains("--")) {
                    optionsFound.add(OPTIONS_LONG.get(arg));
                } else {
                    optionsFound.add(OPTIONS_SHORT.get(arg));
                }
            }
        }
        if (arguments.length < 2) {
            throw new IOException("convti: not enough arguments given; try --help if you need it");
        }
    }

    private void storeFilename(String filename) throws IOException {
        switch (this.numFileArgs) {
            case 0 -> {
                ++this.numFileArgs;
                this.inputFilename = filename;
            }
            case 1 -> {
                ++this.numFileArgs;
                this.outputFilename = filename;
            }
            default -> {
                throw new IOException("convti: too many files given");
            }
        }
    }

    private void catchFormattingError(String[] allArguments, int position) throws IOException {
        if (position >= allArguments.length) {
            throw new IOException("convti: format specified, but no format given. "); // `no format given` is only thrown as an error when at the end, since `invalid format` will be thrown if at the beginning.
        }
        if (!FORMATS.contains(allArguments[position])) {
            throw new IOException(String.format("convti: invalid format `%s` given. Did you forget to enter a format?", allArguments[position]));
        }
    }

    private void printHelp() {
        System.out.println("usage: convti [<options>] <source> <filename>\n");
        System.out.println("<source>\t\tname of source file to compile");
        System.out.println("<filename>\t\tdesired filename on calculator");
        System.out.println("\noptions:");
        System.out.println("-h, --help\t\tprints this message and quits");
        System.out.println("-v. --version\t\tprints version and quits");
        System.out.println("-f, --format [format]\tspecifies file format (if not provided, this will be inferred using filename and contents)");
        System.out.println("\nValid formats:\nrlist\t\t\treal list\nclist\t\t\tcomplex list\nmatrix\t\t\tmatrix\nstring\t\t\tstring"); // yes this is kinda a dumb way to do this
        System.exit(0);
    }

    private void printVersion() {
        System.out.println("convti version 0.1.0 (alpha)");
        System.exit(0);
    }

    public String getFormat() {
        return this.formatAs;
    }

    public String getInputFilename() {
        return this.inputFilename;
    }

    public String getOutputFilename() {
        return this.outputFilename;
    }
}