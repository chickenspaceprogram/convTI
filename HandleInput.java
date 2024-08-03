import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

public class HandleInput {
    private final HashSet<String> ALL_OPTIONS = new HashSet<>(Arrays.asList("-h", "--help", "-v", "--version", "-f", "--format")); // there is probably a better way to do this
    private final HashSet<String> FORMATS = new HashSet<>(Arrays.asList("rlist", "clist", "matrix", "string"));
    private String formatAs;

    public void getInput(String[] arguments) throws IOException {
        validateOptions(arguments);

        for (int i = 0; i < arguments.length; ++i) {
            if (ALL_OPTIONS.contains(arguments[i])) {
                ++i;
                formatAs = arguments[i];
            }
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
        System.out.println("\nValid formats:\nrlist\t\t\treal list\nclist\t\t\tcomplex lsit\nmatrix\t\t\tmatrix\nstring\t\t\tstring"); // yes this is kinda a dumb way to do this
        System.exit(0);
    }

    private void printVersion() {
        System.out.println("convti version 0.1.0 (alpha)");
        System.exit(0);
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
            if (arg.contains("-") && !ALL_OPTIONS.contains(arg)) {
                throw new IOException("convti: invalid optional argument given; enter `convti --help` to see a list of valid arguments.");
            }
            if (arg.contains("-")) {
                optionsFound.add(arg);
            }
        }
        if (arguments.length < 2) {
            throw new IOException("convti: not enough arguments given; try --help if you need it");
        }
    }
}