package com.HexaStudio.IllusionScript.VM;

public enum Instructions {
    HALT("HALT", 0x40000000),
    ADD("ADD", 0x40000001),
    SUB("SUB", 0x40000002),
    MUL("MUL", 0x40000003),
    DIV("DIV", 0x40000004),
    INVOKE("INVOKE", 0x40000005),
    JUMP("JUMP", 0x40000006),
    RETURN("RETURN", 0x40000007),
    LOAD_REG("LOAD_REG", 0x40000008),
    SAVE_REG("SAVE_REG", 0x40000009),
    LOAD("LOAD", 0x4000000A),
    SAVE("SAVE", 0x4000000B),
    SWAP("SWAP", 0x4000000C),
    DUP("DUP", 0x4000000D),
    INVOKE_NATIVE("INVOKE_VIRTUAL", 0x4000000E);

    public final String name;
    public final int value;

    private Instructions(String name, int value) {
        this.name = name;
        this.value = value;
    }
}
