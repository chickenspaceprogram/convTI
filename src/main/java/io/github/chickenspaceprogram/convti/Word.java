package io.github.chickenspaceprogram.convti;
public class Word {
    private byte MSB;
    private byte LSB;

    public Word(short value) {
        LSB = (byte) (value & 0x00FF); // LSB
        MSB = (byte) ((value & 0xFF00) >> 8); // MSB
    }

    public Word() {
        LSB = 0x00;
        MSB = 0x00;
    }

    public void setWord(short value) {
        LSB = (byte) (value & 0x00FF);
        MSB = (byte) ((value & 0xFF00) >> 8);
    }

    public byte getLSB() {
        return LSB;
    }

    public void setLSB(byte value) {
        LSB = value;
    }

    public byte getMSB() {
        return MSB;
    }

    public void setMSB(byte value) {
        MSB = value;
    }
}