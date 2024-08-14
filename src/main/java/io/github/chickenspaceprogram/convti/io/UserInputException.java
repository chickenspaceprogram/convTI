package io.github.chickenspaceprogram.convti.io;

/**
 * A UserInputException is to be thrown when the user has formatted their input incorrectly.
 */
public class UserInputException extends Exception {
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

    /**
     * Constructs a new UserInputException with a detail message and a cause.<br>
     * Allows suppression and the stack trace to be configured.
     *
     * @param  message the detail message
     * @param cause the cause
     * @param enableSuppression whether to enable suppression
     * @param writableStackTrace whether the stack trace is writeable
     */
    protected UserInputException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}