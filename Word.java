public class Word {
    public byte MSB;
    public byte LSB;

    public void makeWord(short value) {
        this.LSB = (byte) (value & 0x00FF); // LSB
        this.MSB = (byte) ((value & 0xFF00) >> 8); // MSB
    }
}