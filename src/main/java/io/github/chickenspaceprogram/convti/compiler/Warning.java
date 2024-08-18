package io.github.chickenspaceprogram.convti.compiler;

/**
 * Signals that a non-fatal issue has occurred that the user should be aware of.
 * For example, entering a number with an exponent that is too large or too small
 */
public class Warning extends Exception {
    private int row;
    private int col;

    /**
     * Constructs a new Warning with a detail message but no information about location.
     * 
     * @param message the detail message
     */
    public Warning(String message) {
        super(message);
    }

    /**
     * Constructs a new Warning with a detail message and the row and column on which the issue occurred.
     * 
     * @param message the detail message
     * @param row the row on which the issue occurred
     * @param col the column on which the issue occurred
     */
    public Warning(String message, int row, int col) {
        super(message);
        this.row = row;
        this.col = col;
    }

    /**
     * Constructs a new Warning with the row and column on which the issue occurred but no detail message.
     * 
     * @param row the row on which the issue occurred
     * @param col the column on which the issue occurred
     */
    public Warning(int row, int col) {
        super();
        this.row = row;
        this.col = col;
    }

    /**
     * Returns the row on which the issue occurred.
     * 
     * @return the row on which the issue occurred.
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns the column on which the issue occurred.
     * 
     * @return the column on which the issue occurred.
     */
    public int getCol() {
        return col;
    }

    /**
     * Returns a neatly-formatted version of the warning message.
     * 
     * @return the formatted warning message
     */
    public String getWarningMessage() {
        return String.format("Row %d, Column %d: Warning: %s", row, col, getMessage());
    }
}