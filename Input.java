import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

public class Input {
    final HashSet<String> allOptions = new HashSet<>(Arrays.asList("-h", "--help", "-v", "--version", "-f", "--format")); // there is probably a better way to do this
    final HashSet<String> formats = new Hashset<>(Arrays.asList(""));
    String formatAs;

    public Input(String[] arguments) {
        if (arguments.length <= 2) {
            printHelp();
        }
        validateOptions(arguments);

        for (int i = 0; i < arguments.length; ++i) {
            if (allOptions.contains(arguments[i])) {
                ++i;
                formatAs = arguments[i];
            }
        }
    }
    
    private void printHelp() {
        System.out.println("usage: convti [<options>] <source> <filename>");
        System.out.println("<source>\tname of source file to compile");
        System.out.println("<filename>\tdesired filename on calculator");
        System.out.println("options:");
        System.out.println("-h, --help\tprints this message");
        System.out.println("-v. --version\tprints version");
        System.out.println("-f, --format\tspecifies file format (if not provided, this will be inferred using filename and contents)");
        System.out.println("\t\tValid formats\nreal\treal number\ncplx\ncomplex number\nlistreal\nreal list\nlistcplx\ncomplex lsit\nmatrix\nmatrix\nstring\nstring");
        System.exit(0);
    }

    private void printVersion() {
        System.out.println("convti version 0.1.0 (alpha)");
        System.exit(0);
    }

    private void validateOptions(String[] arguments) {
        HashSet<String> optionsFound = new HashSet<>();
        try {
            for (String arg : arguments) {
                switch (arg) {
                    case "-h", "--help" -> printHelp();
                    case "-v", "--version" -> printVersion();
                }
                if (optionsFound.contains(arg)) {
                    throw new IOException("Optional argument was listed twice.");
                }
                if (arg.contains("-") && !allOptions.contains(arg)) {
                    throw new IOException("Invalid optional argument given.");
                }
                if (arg.contains("-")) {
                    optionsFound.add(arg);
                }
            }
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
        }
    }
}