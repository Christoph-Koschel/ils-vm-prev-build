package com.HexaStudio.IllusionScript.VM.Builder.Instructions;

import com.HexaStudio.IllusionScript.VM.Builder.InstructionWrapper;

public class StaticValue implements InstructionWrapper {
    private final int value;

    public StaticValue(int value) {

        this.value = value;
    }

    @Override
    public int toByte() {
        return value;
    }
}
