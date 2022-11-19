package com.HexaStudio.IllusionScript.VM;

import java.util.HashMap;

public class Helper {
    protected static HashMap<String, Integer> functions = new HashMap<>();
    protected static int[] globalProgram = new int[0];

    protected static HashMap<String, Integer> functionsSize = new HashMap<>();

    public static void buildFunction(String name, int parameters, int[] code) {
        functions.put(name, globalProgram.length);
        functionsSize.put(name, code.length);
        globalProgram = merge_array(globalProgram, code);
    }

    public static int[] buildCall(String name, int count) {
        if (functions.containsKey(name)) {
            int addr = functions.get(name);

            return new int[]{
                    addr,
                    count,
                    Instructions.INVOKE.value
            };
        }
        return new int[0];
    }

    public static int[] buildNativeCall(String name, int count) {
        if (NativeFunction.numbers.containsKey(name)) {
            int addr = NativeFunction.numbers.get(name);

            return new int[]{
                    addr,
                    count,
                    Instructions.INVOKE_NATIVE.value
            };
        }

        return new int[0];
    }

    public static int[] buildFunctionStart() {
        return new int[]{
                2,
                Instructions.LOAD_REG.value,
                1,
                Instructions.LOAD_REG.value,
                2,
                Instructions.SUB.value,
                2,
                Instructions.SWAP.value,
                Instructions.SAVE_REG.value,
                2,
                Instructions.LOAD_REG.value,
                Instructions.DUP.value,
                Instructions.LOAD.value,
                2,
                Instructions.ADD.value,
                Instructions.SUB.value,
                2,
                Instructions.SWAP.value,
                Instructions.SAVE_REG.value
        };
    }

    public static int[] buildNopReturn() {
        return new int[]{
                2,
                Instructions.LOAD_REG.value,
                Instructions.DUP.value,
                1,
                Instructions.ADD.value,
                Instructions.DUP.value,
                Instructions.LOAD.value,
                2,
                Instructions.ADD.value,
                Instructions.ADD.value,
                Instructions.LOAD.value,
                2,
                Instructions.SWAP.value,
                Instructions.SAVE_REG.value,
                1,
                Instructions.SWAP.value,
                2,
                Instructions.ADD.value,
                Instructions.SAVE_REG.value,
                Instructions.RETURN.value
        };
    }

    public static int[] buildReturn(int value) {
        return new int[]{
                2,
                Instructions.LOAD_REG.value,
                Instructions.DUP.value,
                1,
                Instructions.ADD.value,
                Instructions.DUP.value,
                Instructions.LOAD.value,
                2,
                Instructions.ADD.value,
                Instructions.ADD.value,
                Instructions.LOAD.value,
                2,
                Instructions.SWAP.value,
                Instructions.SAVE_REG.value,
                1,
                Instructions.SWAP.value,
                2,
                Instructions.ADD.value,
                Instructions.SAVE_REG.value,
                value,
                Instructions.SWAP.value,
                Instructions.RETURN.value
        };
    }

    public static int[] buildStackReturn() {
        return new int[]{

        };
    }

    public static int[] merge_array(int[] left, int[] right) {
        int[] x = new int[left.length + right.length];
        for (int i = 0; i < left.length; i++) {
            x[i] = left[i];
        }

        for (int i = 0; i < right.length; i++) {
            x[i + left.length] = right[i];
        }

        return x;
    }
}
