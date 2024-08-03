import java.io.IOException;

public class convti {
    public static void main(String[] args) {
        Input config = new HandleInput();
        try {
            config.getInput(args);
        } catch (IOException error) {
            System.err.println(error.getMessage());
            System.exit(-1);
        }
        System.out.println(config.getFormat());
        System.out.println(config.getInputFilename());
        System.out.println(config.getOutputFilename());
        
    }
}
// apologies if this code is actually horrible or something, I'm still in the process of learning Java and OOP in general.