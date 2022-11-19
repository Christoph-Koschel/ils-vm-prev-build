package com.HexaStudio.IllusionScript.VM;

public interface NativeFunctionWrapper {
    int exec(int[] params, StackVM machine);

    String getName();

    boolean hasReturn();
}
