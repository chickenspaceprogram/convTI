package io.github.chickenspaceprogram.convti.compiler;

public class RealNumber {
    /**
     * Constructs an object representing TI's real number format given a string representing that number as input.
     * The string can represent an integer, decimal, or a number in scientific notation.
     * For example, `123`, `12.34`, `1.2345e+67`, and `123.456E-78` are all valid, although the latter will give a warning to the user (due to the significand not being between 1 and 10).
     * @param number the string to be converted into a RealNumber
     * @param suppressWarnings if true, no compiler warnings will be given
     */
    public RealNumber(String number, boolean suppressWarnings) throws Warning {

    }
}