package io.github.chickenspaceprogram.convti;

class UserInputException extends Exception {
    /**
     * Constructs a class of exception to be thrown when the user has entered or formatted their input incorrectly.
     */
    public UserInputException() {}

    public UserInputException(String msg) {
        super(msg);
    }

    public UserInputException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public UserInputException(Throwable cause) {
        super(cause);
    }

    public UserInputException(String msg, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(msg, cause, enableSuppression, writableStackTrace);
    }
}