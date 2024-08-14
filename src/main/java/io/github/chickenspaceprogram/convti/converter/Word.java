package io.github.chickenspaceprogram.convti.converter;

/**
 * A class that separates a short into its most and least significant bytes.
 */
public class Word {
    private byte MSB;
    private byte LSB;

    /**
     * Initializes the most and least significant bytes of the word.
     */
    public Word(short value) {
        LSB = (byte) (value & 0x00FF); // LSB
        MSB = (byte) ((value & 0xFF00) >> 8); // MSB
    }

    /**
     * Sets the most and least significant bytes of the word to those of value.
     */
    public void setWord(short value) {
        LSB = (byte) (value & 0x00FF);
        MSB = (byte) ((value & 0xFF00) >> 8);
    }

    /**
     * Returns the least significant byte of the word.
     */
    public byte getLSB() {
        return LSB;
    }

    /**
     * Returns the most significant byte of the word.
     */
    public byte getMSB() {
        return MSB;
    }
}