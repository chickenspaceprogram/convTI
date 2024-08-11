package io.github.chickenspaceprogram.convti;
import java.nio.charset.StandardCharsets;

public class FileHeader {
    private final String eightSig = "**TI83F*";
    private final byte[] threeSig = {0x1A, 0x0A, 0x00};
    private final String comment = "Made by convTI, a TI calc file converter. ";
    private final int arrayLen = 55; // headers are always the same length
    private final byte[] headerArray = new byte[arrayLen];
    private final Word checksum;

    private final byte[] eightSigBytes = eightSig.getBytes(StandardCharsets.US_ASCII);
    private final byte[] commentBytes = comment.getBytes(StandardCharsets.US_ASCII);

    public FileHeader(Word dataLength, byte[] data) {
        System.arraycopy(eightSigBytes, 0, headerArray, 0, 8);
        System.arraycopy(threeSig, 0, headerArray, 8, 3);
        System.arraycopy(commentBytes, 0, headerArray, 11, 42);
        headerArray[53] = dataLength.getLSB();
        headerArray[54] = dataLength.getMSB();
        checksum = new Word(calculateChecksum(data));
    }

    public Word getChecksum() {
        return checksum;
    }

    private short calculateChecksum(byte[] data) {
        short sum = 0;
        for (byte currentByte : data) {
            sum += (short) Byte.toUnsignedInt(currentByte);
        }
        return sum;
    }


}