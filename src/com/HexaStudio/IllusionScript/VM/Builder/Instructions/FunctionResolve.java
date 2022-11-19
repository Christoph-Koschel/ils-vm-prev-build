package com.HexaStudio.IllusionScript.VM.Builder.Instructions;

import com.HexaStudio.IllusionScript.VM.Builder.InstructionWrapper;

public class FunctionResolve implements InstructionWrapper {
    public final String target;

    public FunctionResolve(String target) {
        this.target = target;
    }

    @Override
    public int toByte() {
        return 0;
    }
}
