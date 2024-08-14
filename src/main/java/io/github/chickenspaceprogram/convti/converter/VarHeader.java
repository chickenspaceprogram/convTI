package io.github.chickenspaceprogram.convti.converter;
import java.util.HashMap;
import java.util.Map;

public class VarHeader {
    /**
     * Creates the header for a specific variable entry.
     */
    // there is almost certainly a better way to deal with variable tokens & typeIDs, but this was easy
    // this class just generally is messy, but I don't really see how I could've made it *not* messy.
    private final String[] typesArray = {"rnum", "rlist", "matrix", "yvar", "string", "prgm", "elprgm", "pic", "gdb", "cnum", "clist", "tbl"};

    private HashMap<String, Byte> allTypeIDs;

    private final Map<String, Short> MATRIX_TOKENS = Map.of(
        "[A]", (short) 0x00AA,
        "[B]", (short) 0x01AA,
        "[C]", (short) 0x02AA,
        "[D]", (short) 0x03AA,
        "[E]", (short) 0x04AA,
        "[F]", (short) 0x05AA,
        "[G]", (short) 0x06AA,
        "[H]", (short) 0x07AA,
        "[I]", (short) 0x08AA,
        "[J]", (short) 0x09AA
    );
    private final Map<String, Short> STRING_TOKENS = Map.of(
        "Str1", (short) 0x005C,
        "Str2", (short) 0x015C,
        "Str3", (short) 0x025C,
        "Str4", (short) 0x035C,
        "Str5", (short) 0x045C,
        "Str6", (short) 0x055C,
        "Str7", (short) 0x065C,
        "Str8", (short) 0x075C,
        "Str9", (short) 0x085C,
        "Str0", (short) 0x095C
    );
    // currently implicitly assuming that if the user enters `L1` or `l1` they want to use the inbuilt L1 token, may add flag for this behavior in future
    private final Map<String, Short> LIST_TOKENS = Map.of(
        "L1", (short) 0x005D,
        "L2", (short) 0x015D,
        "L3", (short) 0x025D,
        "L4", (short) 0x035D,
        "L5", (short) 0x045D,
        "L6", (short) 0x055D
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
        /**
         * Creates the header for a variable entry and checks for errors.
         */
        this.dataLength = new Word(dataLength);
        fillTypeIDs();
        typeID = allTypeIDs.get(type);
        setVarName(filename, type);
        setArchivedStatus(isArchived);
        // 17 bytes is the size of the data section's header.
        if ((dataLength & 0xFFFF) + 17 > 65535) {
            throw new IllegalArgumentException("Data section of variable too large, encountered overflow.");
        }
        dataSectionLength = (short) (dataLength + 17);
        fillHeader();
    }

    public short getDataSectionLength() {
        /**
         * Returns the length of the entire variable section, including the variable header.
         */
        return dataSectionLength;
    }

    public byte[] getDataHeader() {
        /**
         * Returns the variable header.
         */
        return header;
    }

    private void setVarName(String name, String type) throws IllegalArgumentException{
        /**
         * Checks whether the variable's name is valid.
         * If the name is valid, it is stored to varName in the proper format.
         * If the name is invalid, an IllegalArgumentException is thrown.
         */
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
                if (name.length() > 5) {
                    throw new IllegalArgumentException("Invalid list name, name must be equal to or fewer than 5 characters.");
                }
                varName[0] = (byte) 0x5D;
                if (LIST_TOKENS.containsKey(name)) {
                    Word nameWord = new Word(LIST_TOKENS.get(name));
                    varName[1] = nameWord.getMSB();
                } else if (name.matches("L[0-9A-F]{2}")) {
                    varName[1] = Byte.parseByte(name.substring(3, 5), 16);
                } else {
                    byte[] nameBytes = name.getBytes();
                    for (int i = 1; i < nameBytes.length; ++i) {
                        varName[i] = nameBytes[i];
                    }
                }
            }
            case "string" -> {
                if (name.matches("STR[0-9A-F]{2}")) {
                    varName[0] = (byte) 0x5C;
                    varName[1] = Byte.parseByte(name.substring(5, 7), 16);
                } else if (STRING_TOKENS.containsKey(name)) {
                    final Word nameWord = new Word(STRING_TOKENS.get(name));
                    varName[0] = nameWord.getLSB();
                    varName[1] = nameWord.getMSB();
                } else {
                    throw new IllegalArgumentException("Invalid string name. Valid strings are `Str1`-`Str0` or `StrNN`, where NN is a byte in hexadecimal.");
                }
            }
            case "matrix" -> {
                if (name.matches("\\[[0-9A-F]{2}\\]")) {
                    varName[0] = (byte) 0xAA;
                    varName[1] = Byte.parseByte(name.substring(3, 5), 16);
                } else if (MATRIX_TOKENS.containsKey(name)) {
                    final Word nameWord = new Word(MATRIX_TOKENS.get(name));
                    varName[0] = nameWord.getLSB();
                    varName[1] = nameWord.getMSB();
                } else {
                    throw new IllegalArgumentException("Invalid matrix name, only valid matrices are `[A]`-`[J]` or [NN], where NN is a byte in hexadecimal.");
                }
                
            }
        }
    }

    private void setArchivedStatus(boolean isArchived) {
        /**
         * Sets the bytes that tell the calculator whether the variable is to be archived.
         */
        if (isArchived) {
            archivedStatus = (byte) 0x80;
        } else {
            archivedStatus = (byte) 0x00;
        }
    }

    private void fillHeader() {
        /**
         * Puts all the separate parts of the header into an array of bytes.
         */
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
    
    private void fillTypeIDs() {
        /**
         * Creates a HashMap mapping variable types to their corresponding TypeIDs.
         */
        byte currentTypeID = 0x00;
        for (String type : typesArray) {
            allTypeIDs.put(type, currentTypeID);
            switch (type) {
                case "gdb" -> {
                    currentTypeID = 0x0C;
                }
                case "tbl" -> {
                    currentTypeID = 0x11;
                }
                default -> {
                    ++currentTypeID;
                }
            }
        }
    }
}