package io.github.chickenspaceprogram.convti.converter;
public class Word {
    /**
     * A class that separates a short into its most and least significant bytes.
     */
    private byte MSB;
    private byte LSB;

    public Word(short value) {
        /**
         * Initializes the most and least significant bytes of the word.
         */
        LSB = (byte) (value & 0x00FF); // LSB
        MSB = (byte) ((value & 0xFF00) >> 8); // MSB
    }

    public void setWord(short value) {
        /**
         * Sets the most and least significant bytes of the word to those of value.
         */
        LSB = (byte) (value & 0x00FF);
        MSB = (byte) ((value & 0xFF00) >> 8);
    }

    public byte getLSB() {
        /**
         * Returns the least significant byte of the word.
         */
        return LSB;
    }

    public byte getMSB() {
        /**
         * Returns the most significant byte of the word.
         */
        return MSB;
    }
}