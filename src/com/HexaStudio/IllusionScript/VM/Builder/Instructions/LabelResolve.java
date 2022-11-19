package com.HexaStudio.IllusionScript.VM.Builder.Instructions;

import com.HexaStudio.IllusionScript.VM.Builder.InstructionWrapper;

import java.util.HashMap;

public class LabelResolve implements InstructionWrapper {
    public final Label label;

    public LabelResolve(Label label) {
        this.label = label;
    }

    @Override
    public int toByte() {
        return 0;
    }
}
