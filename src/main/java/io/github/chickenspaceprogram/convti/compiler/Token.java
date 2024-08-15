package io.github.chickenspaceprogram.convti.compiler;

/**
 * A class representing a token for the TI-8x series of calculators, including all its associated metadata.
 */
public class Token {
    private final boolean isShort;
    private final String regex;
    private final String model;
    private final String osversion;
    private final byte[] value;

    /**
     * Constructor that initializes a two-byte token.<br>
     * Throws IllegalArgumentException if the given array doesn't have a length of 2 (to create a 1-byte token, pass the byte itself as an argument, not a byte array).
     * @param regex the regex that identifies the token
     * @param value the bytes that the token is represented as on the calculator
     * @param model the oldest model of calculator that supports this token
     * @param osversion the oldest OS version that supports this token
     */
    public Token(String regex, byte[] value, String model, String osversion) throws IllegalArgumentException {
        if (value.length != 2) {
            throw new IllegalArgumentException(String.format("Passed a byte array of incorrect length (length=%d)", value.length));
        }
        this.regex = regex;
        this.value = value;
        this.model = model;
        this.osversion = osversion;
        isShort = true;
    }

    /**
     * Constructor that initializes a one-byte token.
     * @param regex the regex that identifies the token
     * @param value the byte that the token is represented as on the calculator
     * @param model the oldest model of calculator that supports this token
     * @param osversion the oldest OS version that supports this token
     */
    public Token(String regex, byte value, String model, String osversion) {
        this.regex = regex;
        this.value = new byte[1];
        this.value[0] = value;
        this.model = model;
        this.osversion = osversion;
        isShort = false;
    }

    /**
     * Returns the regular expression that identifies this token.
     * @return the regular expression that identifies this token
     */
    public String getRegex() {
        return regex;
    }

    /**
     * Returns the value of this token as an array of bytes, array length will be either one or two.
     * @return the byte(s) that the token is represented as on the calculator
     */
    public byte[] getValue() {
        assert ((value.length == 1) || (value.length == 2)) : "Token was not 1 or 2 bytes long.";
        return value;
    }

    /**
     * Returns the oldest calculator model that supports this token.
     * @return the oldest calculator model that supports this token
     */
    public String getModel() {
        return model;
    }

    /**
     * Returns the earliest OS version that supports this token.
     * @return the earliest OS version that supports this token
     */
    public String getOSVersion() {
        return osversion;
    }

    /**
     * Returns whether the token is two bytes long.
     * @return whether the token is two bytes long
     */
    public boolean isTwoByte() {
        return isShort;
    }
}