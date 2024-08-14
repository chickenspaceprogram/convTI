package io.github.chickenspaceprogram.convti.converter;

public class Token {
    /**
     * A class representing a token for the TI-8x series of calculators, including all its associated metadata.
     */
    private final boolean isShort;
    private final String regex;
    private final String model;
    private final String osversion;
    private final byte[] value;

    public Token(String regex, byte[] value, String model, String osversion) throws IllegalArgumentException {
        /**
         * Constructor that initializes a two-byte token.
         * Throws IllegalArgumentException if the given array doesn't have a length of 2 (to create a 1-byte token, pass the byte itself as an argument, not a byte array).
         */
        if (value.length != 2) {
            throw new IllegalArgumentException(String.format("Passed a byte array of incorrect length (length=%d)", value.length));
        }
        this.regex = regex;
        this.value = value;
        this.model = model;
        this.osversion = osversion;
        isShort = true;
    }

    public Token(String regex, byte value, String model, String osversion) {
        /**
         * Constructor that initializes a one-byte token.
         */
        this.regex = regex;
        this.value = new byte[1];
        this.value[0] = value;
        this.model = model;
        this.osversion = osversion;
        isShort = false;
    }

    public String getRegex() {
        /**
         * Returns the regular expression that identifies this token.
         */
        return regex;
    }

    public byte[] getValue() {
        /**
         * Returns the value of this token as an array of bytes, array length will be either one or two.
         */
        return value;
    }

    public String getModel() {
        /**
         * Returns the oldest calculator model that supports this token.
         */
        return model;
    }

    public String getOSVersion() {
        /**
         * Returns the earliest OS version that supports this token.
         */
        return osversion;
    }

    public boolean isTwoByte() {
        /**
         * Returns whether the token is two bytes long.
         */
        return isShort;
    }
}