import java.nio.charset.StandardCharsets;

public class FileHeader {
    String eightSig = "**TI83F*";
    byte[] threeSig = {0x1a, 0x0a, 0x00};
    String comment = "Made by convTI, a TI calc file converter. ";
    int arrayLen = 55; // headers are always the same length
    byte[] headerArray = new byte[arrayLen];

    byte[] eightSigBytes = eightSig.getBytes(StandardCharsets.US_ASCII);
    byte[] commentBytes = comment.getBytes(StandardCharsets.US_ASCII);

    public FileHeader(Word dataLength) {
        System.arraycopy(eightSigBytes, 0, headerArray, 0, 8);
        System.arraycopy(threeSig, 0, headerArray, 8, 3);
        System.arraycopy(commentBytes, 0, headerArray, 11, 42);
        headerArray[53] = dataLength.getLSB();
        headerArray[54] = dataLength.getMSB();
    }

    public Word checksum() {
        return new Word((short) 1234); // this line only here so that the debugger stops yelling at me
    }
}