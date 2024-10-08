package io.github.chickenspaceprogram.convti.compiler;
import java.nio.charset.StandardCharsets;

/**
 * Creates the header for the file to be sent to the calculator.
 */
public class FileHeader {
    private final String eightSig = "**TI83F*";
    private final byte[] threeSig = {0x1A, 0x0A, 0x00};
    private final String comment = "Made by convTI, a TI calc file converter. ";
    private final int arrayLen = 55; // headers are always the same length
    private final byte[] headerArray = new byte[arrayLen];

    private final byte[] eightSigBytes = eightSig.getBytes(StandardCharsets.US_ASCII);
    private final byte[] commentBytes = comment.getBytes(StandardCharsets.US_ASCII);

    /**
     * Creates the header for the file to be sent to the calculator.
     * @param dataLength the length of the entire data section
     */
    public FileHeader(Word dataLength) {
        System.arraycopy(eightSigBytes, 0, headerArray, 0, 8);
        System.arraycopy(threeSig, 0, headerArray, 8, 3);
        System.arraycopy(commentBytes, 0, headerArray, 11, 42);
        headerArray[53] = dataLength.getLSB();
        headerArray[54] = dataLength.getMSB();
    }

    /**
     * Returns the bytes of the file's header.
     * @return the file's header
     */
    public byte[] getHeader() {
        return headerArray;
    }


}