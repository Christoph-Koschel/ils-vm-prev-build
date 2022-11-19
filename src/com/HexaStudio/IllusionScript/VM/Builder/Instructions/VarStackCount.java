package com.HexaStudio.IllusionScript.VM.Builder.Instructions;

import com.HexaStudio.IllusionScript.VM.Builder.FunctionVisitor;
import com.HexaStudio.IllusionScript.VM.Builder.InstructionWrapper;

public class VarStackCount implements InstructionWrapper {
    private final FunctionVisitor fv;

    public VarStackCount(FunctionVisitor fv) {
        this.fv = fv;
    }

    @Override
    public int toByte() {
        return  fv.getVarStack() + 1;
    }
}
