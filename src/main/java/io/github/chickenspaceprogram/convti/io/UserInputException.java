package io.github.chickenspaceprogram.convti.io;

import java.io.IOException;
/**
 * A UserInputException is to be thrown when the user has formatted their input incorrectly.
 */
public class UserInputException extends IOException {
    /**
     * Constructs a new UserInputException with no detail message.
     */
    public UserInputException() {}

    /**
     * Constructs a new UserInputException with a detail message.
     * 
     * @param message the detail message
     */
    public UserInputException(String message) {
        super(message);
    }
}