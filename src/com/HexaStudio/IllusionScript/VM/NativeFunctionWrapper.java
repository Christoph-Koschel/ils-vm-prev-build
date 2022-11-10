package com.HexaStudio.IllusionScript.VM;

public interface NativeFunctionWrapper {
    int exec(int[] params);

    String getName();

    boolean hasReturn();
}
