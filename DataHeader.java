import java.util.Map;

public class DataHeader {
    // there is almost certainly a better way to deal with variable tokens & typeIDs, but this was easy
    // this class just generally is messy, but I don't really see how I could've made it *not* messy.
    private final Map<String, Byte> TYPE_ID = Map.of(
        "rlist", (byte) 0x01,
        "clist", (byte) 0x0d,
        "matrix", (byte) 0x02,
        "string", (byte) 0x04
    );
    private final Map<String, Short> MATRIX_TOKENS = Map.of(
        "[A]", (short) 0x00aa,
        "[B]", (short) 0x01aa,
        "[C]", (short) 0x02aa,
        "[D]", (short) 0x03aa,
        "[E]", (short) 0x04aa,
        "[F]", (short) 0x05aa,
        "[G]", (short) 0x06aa,
        "[H]", (short) 0x07aa,
        "[I]", (short) 0x08aa,
        "[J]", (short) 0x09aa
    );
    private final Map<String, Short> STRING_TOKENS = Map.of(
        "Str1", (short) 0x005c,
        "Str2", (short) 0x015c,
        "Str3", (short) 0x025c,
        "Str4", (short) 0x035c,
        "Str5", (short) 0x045c,
        "Str6", (short) 0x055c,
        "Str7", (short) 0x065c,
        "Str8", (short) 0x075c,
        "Str9", (short) 0x085c,
        "Str0", (short) 0x095c
    );
    // currently implicitly assuming that if the user enters `L1` or `l1` they want to use the inbuilt L1 token, may add flag for this behavior in future
    private final Map<String, Short> LIST_TOKENS = Map.of(
        "L1", (short) 0x005d,
        "L2", (short) 0x015d,
        "L3", (short) 0x025d,
        "L4", (short) 0x035d,
        "L5", (short) 0x045d,
        "L6", (short) 0x055d,
        "L7", (short) 0x065d,   // yes, `L7`-`L0` exist! the tokens don't display correctly (they display as Y1-Y4) but you can still use them as lists
        "L8", (short) 0x075d,
        "L9", (short) 0x085d,
        "L0", (short) 0x095d
    );
    private final Word startWord = new Word((short) 0x0d); 
    private final Word dataLength;
    private final byte typeID;
    private final byte[] varName = new byte[8];
    private final byte version = (byte) 0x00;
    private byte archivedStatus; // making this final gave errors, idk why
    private final byte[] header = new byte[17];
    private final short dataSectionLength;

    public DataHeader(String filename, String type, short dataLength, boolean isArchived) throws IllegalArgumentException {

        this.dataLength = new Word(dataLength);
        typeID = TYPE_ID.get(type);
        setVarName(filename, type);
        setArchivedStatus(isArchived);
        // 17 bytes is the size of the data section's header.
        if ((dataLength & 0xffff) + 17 > 65535) {
            throw new IllegalArgumentException("Data section of variable too large, encountered overflow.");
        }
        dataSectionLength = (short) (dataLength + 17);
        fillHeader();
    }

    public short getDataSectionLength() {
        return dataSectionLength;
    }

    public byte[] getDataHeader() {
        return header;
    }

    private void setVarName(String name, String type) throws IllegalArgumentException{
        name = name.toUpperCase();
        if (!name.matches("^[A-Z0-9\\[\\]]*$")) {
            throw new IllegalArgumentException("Invalid variable name, name contained illegal (nonalphanumeric) characters.");
        }
        if (!Character.isLetter(name.charAt(0)) && !type.equals("matrix")) {
            throw new IllegalArgumentException("First character in string was nonalphabetic.");
        }
        // maybe refactor this switch statement?
        switch (type) {
            case "rlist", "clist" -> {
                if (type.length() > 5) {
                    throw new IllegalArgumentException("Invalid list name, name must be equal to or fewer than 5 characters.");
                }
                if (LIST_TOKENS.containsKey(name)) {
                    Word nameWord = new Word(LIST_TOKENS.get(name));
                    varName[0] = nameWord.getLSB();
                    varName[1] = nameWord.getMSB();
                } else {
                    byte[] nameBytes = name.getBytes();
                    for (int i = 0; i < nameBytes.length; ++i) {
                        varName[i] = nameBytes[i];
                    }
                }
            }
            case "string" -> {
                if (!STRING_TOKENS.containsKey(name)) {
                    throw new IllegalArgumentException("Invalid string name, only valid strings are `Str1` through `Str0`"); // maybe make users enter `1` instead of `Str1`?
                }
                final Word nameWord = new Word(STRING_TOKENS.get(name));
                varName[0] = nameWord.getLSB();
                varName[1] = nameWord.getMSB();
            }
            case "matrix" -> {
                if (!MATRIX_TOKENS.containsKey(name)) {
                    throw new IllegalArgumentException("Invalid matrix name, only valid matrices are `[A]`-`[J]`.");
                }
                final Word nameWord = new Word(MATRIX_TOKENS.get(name));
                varName[0] = nameWord.getLSB();
                varName[1] = nameWord.getMSB();
            }
        }
    }

    private void setArchivedStatus(boolean isArchived) {
        if (isArchived) {
            archivedStatus = (byte) 0x80;
        } else {
            archivedStatus = (byte) 0x00;
        }
    }

    private void fillHeader() {
        // this code is dumb, but hey, it works.
        header[0] = startWord.getLSB();
        header[1] = startWord.getMSB();

        header[2] = dataLength.getLSB();
        header[3] = dataLength.getMSB();

        header[4] = typeID;

        for (int i = 0; i < 8; ++i) {
            header[i + 5] = varName[i];
        }

        header[13] = version;

        header[14] = archivedStatus;

        header[15] = dataLength.getLSB();
        header[16] = dataLength.getMSB();
    }
    
}