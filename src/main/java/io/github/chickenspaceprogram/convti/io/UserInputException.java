package io.github.chickenspaceprogram.convti.io;

import java.io.IOException;
/**
 * A UserInputException is to be thrown when the user has formatted their input incorrectly.
 */
public class UserInputException extends IOException {
    /**
     * Constructs a new UserInputException with no cause or detail message.
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

    /**
     * Constructs a new UserInputException with a detail message and a cause.
     *
     * @param message the detail message
     * @param cause the cause
     */
    public UserInputException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new UserInputException with a cause.
     *
     * @param cause the cause
     */
    public UserInputException(Throwable cause) {
        super(cause);
    }
}