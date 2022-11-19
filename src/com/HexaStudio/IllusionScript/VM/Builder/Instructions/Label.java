package com.HexaStudio.IllusionScript.VM.Builder.Instructions;

import com.HexaStudio.IllusionScript.VM.Builder.InstructionWrapper;

public class Label implements InstructionWrapper {
    private static int labelC = 0;
    private int id;

    public Label() {
        id = labelC++;
    }

    @Override
    public int toByte() {
        return 0;
    }
}
