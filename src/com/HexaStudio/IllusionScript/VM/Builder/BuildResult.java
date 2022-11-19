package com.HexaStudio.IllusionScript.VM.Builder;

import java.util.HashMap;

public class BuildResult {
    private final int[] code;
    private final HashMap<String, Integer> functions;

    public BuildResult(int[] code, HashMap<String, Integer> functions) {

        this.code = code;
        this.functions = functions;
    }

    public int[] getCode() {
        return code;
    }

    public int getFunctionPoint(String name) {
        return functions.get(name);
    }
}
