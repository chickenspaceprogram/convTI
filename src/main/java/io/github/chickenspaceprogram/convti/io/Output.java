package io.github.chickenspaceprogram.convti.io;

/**
 * Writes the converted files to a .8x* file.
 * Not currently implemented as of v1.0.0.
 */
public class Output {

    /**
     * Calculates the file's checksum.
     * @param data the file's data section
     * @return the checksum
     */
    public short calculateChecksum(byte[] data) {
        short sum = 0;
        for (byte currentByte : data) {
            sum += (short) Byte.toUnsignedInt(currentByte);
        }
        return sum;
    }
}