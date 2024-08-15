package io.github.chickenspaceprogram.convti;
import io.github.chickenspaceprogram.convti.io.Input;
import io.github.chickenspaceprogram.convti.io.UserInputException;

/**
 * The main class of this project.
 */
public class convti {
    /**
     * The main function of this project.
     * @param args the arguments (from terminal)
     * @throws UserInputException since I haven't bothered to provide a useful error message yet
     */
    public static void main(String[] args) throws UserInputException {
        Input config;
        try {
            config = new Input(args);
        } catch (UserInputException error) {
            System.err.println(error.getMessage());
            throw error;
        }
        System.out.println(config.getFormat());
        System.out.println(config.getInputFilename());
        System.out.println(config.getOutputFilename());
        
    }
}
