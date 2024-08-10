package io.github.chickenspaceprogram.convti;
import java.util.HashMap;
import java.util.Map;

public class DataHeader {
    // there is almost certainly a better way to deal with variable tokens & typeIDs, but this was easy
    // this class just generally is messy, but I don't really see how I could've made it *not* messy.
    private final String[] typesArray = {"rnum", "rlist", "matrix", "yvar", "string", "prgm", "elprgm", "pic", "gdb", "cnum", "clist", "tbl"};

    private HashMap<String, Byte> allTypeIDs;

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
        "L6", (short) 0x055d
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
        fillTypeIDs();
        typeID = allTypeIDs.get(type);
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
                if (name.length() > 5) {
                    throw new IllegalArgumentException("Invalid list name, name must be equal to or fewer than 5 characters.");
                }
                varName[0] = (byte) 0x5d;
                if (LIST_TOKENS.containsKey(name)) {
                    Word nameWord = new Word(LIST_TOKENS.get(name));
                    varName[1] = nameWord.getMSB();
                } else if (name.matches("L0X[0-9A-F]{2}")) {
                    varName[1] = Byte.parseByte(name.substring(3, 5), 16);
                } else {
                    byte[] nameBytes = name.getBytes();
                    for (int i = 1; i < nameBytes.length; ++i) {
                        varName[i] = nameBytes[i];
                    }
                }
            }
            case "string" -> {
                if (name.matches("STR0X[0-9A-F]{2}")) {
                    varName[0] = (byte) 0x5c;
                    varName[1] = Byte.parseByte(name.substring(5, 7), 16);
                } else if (STRING_TOKENS.containsKey(name)) {
                    final Word nameWord = new Word(STRING_TOKENS.get(name));
                    varName[0] = nameWord.getLSB();
                    varName[1] = nameWord.getMSB();
                } else {
                    throw new IllegalArgumentException("Invalid string name. Valid strings are `Str1`-`Str0` or `Str0xNN`, where NN is a byte in hex.");
                }
            }
            case "matrix" -> {
                if (name.matches("\\[0X[0-9A-F]{2}\\]")) {
                    varName[0] = (byte) 0xaa;
                    varName[1] = Byte.parseByte(name.substring(3, 5), 16);
                } else if (MATRIX_TOKENS.containsKey(name)) {
                    final Word nameWord = new Word(MATRIX_TOKENS.get(name));
                    varName[0] = nameWord.getLSB();
                    varName[1] = nameWord.getMSB();
                } else {
                    throw new IllegalArgumentException("Invalid matrix name, only valid matrices are `[A]`-`[J]` or [0xNN], where NN is a byte in hex.");
                }
                
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
    
    private void fillTypeIDs() {
        byte currentTypeID = 0x00;
        for (String type : typesArray) {
            allTypeIDs.put(type, currentTypeID);
            switch (type) {
                case "gdb" -> {
                    currentTypeID = 0x0c;
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