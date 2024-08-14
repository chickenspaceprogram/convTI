package io.github.chickenspaceprogram.convti.converter;

/**
 * A class that separates a short into its most and least significant bytes and provides a way to access those bytes.
 */
public class Word {
    private byte MSB;
    private byte LSB;

    /**
     * Initializes the most and least significant bytes of the word.
     * @param value value to be separated into MSB and LSB
     */
    public Word(short value) {
        LSB = (byte) (value & 0x00FF); // LSB
        MSB = (byte) ((value & 0xFF00) >> 8); // MSB
    }

    /**
     * Sets the most and least significant bytes of the word to those of value.
     * @param value value to be separated into MSB and LSB
     */
    public void setWord(short value) {
        LSB = (byte) (value & 0x00FF);
        MSB = (byte) ((value & 0xFF00) >> 8);
    }

    /**
     * Returns the least significant byte (LSB) of the word.
     * @return the least significant byte of the word
     */
    public byte getLSB() {
        return LSB;
    }

    /**
     * Returns the most significant byte (MSB) of the word.
     * @return the most significant byte of the word
     */
    public byte getMSB() {
        return MSB;
    }

    /**
     * Returns the MSB and LSB combined (the value of the Word).
     * @return the value of the Word
     */
    public int getWordValue() {
        return ((Byte.toUnsignedInt(MSB) << 8) + Byte.toUnsignedInt(LSB));
    }
}