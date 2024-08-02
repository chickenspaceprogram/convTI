import java.nio.charset.StandardCharsets;

public class Header {
    public byte[] make_array(int data_len) {
        String eightSig = "**TI83F*";
        byte[] threeSig = {0x1a, 0x0a, 0x00};
        String comment = "Made by convTI, a TI calc file converter. ";

        byte[] eightSigBytes = eightSig.getBytes(StandardCharsets.US_ASCII);
        byte[] commentBytes = comment.getBytes(StandardCharsets.US_ASCII);

        if (data_len > 65535) {
            throw new java.lang.Error("Data length provided was too large.");
        }
        byte MSB = (byte) ((data_len & 0xFF00) >> 8);
        byte LSB = (byte) (data_len & 0x00FF);
        int array_length = 55; // headers are always the same length

        byte[] header_array = new byte[array_length];

        System.arraycopy(eightSigBytes, 0, header_array, 0, 8);
        System.arraycopy(threeSig, 0, header_array, 8, 3);
        System.arraycopy(commentBytes, 0, header_array, 11, 42);
        header_array[53] = LSB;
        header_array[54] = MSB;
        return header_array;
    }  
}