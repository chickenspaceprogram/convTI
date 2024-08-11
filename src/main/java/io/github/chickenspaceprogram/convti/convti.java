package io.github.chickenspaceprogram.convti;

public class convti {
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
// apologies if this code is actually horrible or something, I'm still in the process of learning Java and OOP in general.