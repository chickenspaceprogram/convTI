public class Word {
    private byte MSB;
    private byte LSB;

    public Word(short value) {
        LSB = (byte) (value & 0x00FF); // LSB
        MSB = (byte) ((value & 0xFF00) >> 8); // MSB
    }

    public void setWord(short value) {
        LSB = (byte) (value & 0x00FF);
        MSB = (byte) ((value & 0xFF00) >> 8);
    }

    public byte getLSB() {
        return LSB;
    }

    public byte getMSB() {
        return MSB;
    }
}