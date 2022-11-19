package com.HexaStudio.IllusionScript.VM;

import java.util.HashMap;

public class NativeFunction {
    public static HashMap<Integer, NativeFunctionWrapper> functions;
    public static HashMap<String, Integer> numbers;


    static {
        functions = new HashMap<>();
        numbers = new HashMap<>();
    }

    public static void declare(int i, NativeFunctionWrapper func) {
        numbers.put(func.getName(), i);
        functions.put(i, func);
    }

    public static NativeFunctionWrapper get(int name) throws Exception {
        NativeFunctionWrapper func = functions.get(name);
        if (func == null) {
            throw new Exception("Undefined native address");
        }

        return func;
    }
}
