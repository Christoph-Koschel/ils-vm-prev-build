package com.HexaStudio.IllusionScript.VM.Builder.Instructions;

import com.HexaStudio.IllusionScript.VM.Builder.InstructionWrapper;
import com.HexaStudio.IllusionScript.VM.NativeFunction;
import com.HexaStudio.IllusionScript.VM.NativeFunctionWrapper;

public class NativeFunctionResolve implements InstructionWrapper {
    public final String name;

    public NativeFunctionResolve(String name) {
        this.name = name;
    }

    @Override
    public int toByte() {
        return 0;
    }
}
