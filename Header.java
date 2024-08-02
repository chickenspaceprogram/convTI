import java.nio.charset.StandardCharsets;

public class Header {
    String eightSig = "**TI83F*";
    byte[] threeSig = {0x1a, 0x0a, 0x00};
    String comment = "Made by convTI, a TI calc file converter. ";
    int arrayLen = 55; // headers are always the same length
    byte[] headerArray = new byte[arrayLen];

    byte[] eightSigBytes = eightSig.getBytes(StandardCharsets.US_ASCII);
    byte[] commentBytes = comment.getBytes(StandardCharsets.US_ASCII);

    public byte[] makeArray(Word dataLength) {
        System.arraycopy(this.eightSigBytes, 0, this.headerArray, 0, 8);
        System.arraycopy(this.threeSig, 0, this.headerArray, 8, 3);
        System.arraycopy(this.commentBytes, 0, this.headerArray, 11, 42);
        this.headerArray[53] = dataLength.LSB;
        this.headerArray[54] = dataLength.MSB;

        return this.headerArray;
    }
}