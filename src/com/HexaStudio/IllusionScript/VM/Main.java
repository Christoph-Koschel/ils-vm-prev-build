package com.HexaStudio.IllusionScript.VM;

import java.util.ArrayList;

public class Main extends Helper {
    public static void main(String[] args) {
        boolean debug = Utils.includes(args, "-d") || Utils.includes(args, "--debug");
        boolean create = Utils.includes(args, "-c") || Utils.includes(args, "--create");

        {
            int[] code;
            code = merge_array(buildFunctionStart(), new int[]{
                    200,
                    10,
                    Instructions.ADD.value,
                    -50
            });
            code = merge_array(code, buildStackReturn());

            buildFunction("main", 0, code);
        }

        {
            int[] code = new int[0];

            code = merge_array(code, new int[]{
                    0
            });
            code = merge_array(code, buildCall("main", 0));
            code = merge_array(code, new int[]{
                    Instructions.HALT.value
            });

            buildFunction("__entry", 0, code);
        }

        StackVM vm = new StackVM();
        vm.loadFragment(globalProgram);
        vm.run(functions.getOrDefault("__entry", 0), debug);
    }
}
