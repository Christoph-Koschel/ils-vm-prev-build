package com.HexaStudio.IllusionScript.VM.Builder.Instructions;

import com.HexaStudio.IllusionScript.VM.Builder.InstructionWrapper;
import com.HexaStudio.IllusionScript.VM.Instructions;

public class BaseInstruction implements InstructionWrapper {
    private final Instructions instructions;

    public BaseInstruction(Instructions instructions) {
        this.instructions = instructions;
    }

    @Override
    public int toByte() {
        return
                instructions.value;

    }
}
