package com.HexaStudio.IllusionScript.VM;

import java.util.ArrayList;

public class Main extends Helper {
    public static void main(String[] args) {
        boolean debug = Utils.includes(args, "-d") || Utils.includes(args, "--debug");
        boolean create = Utils.includes(args, "-c") || Utils.includes(args, "--create");
        buildNative();

        {
            int[] code;
            code = merge_array(buildFunctionStart(), new int[]{
                    200,
                    10,
                    Instructions.ADD.value
            });
            code = merge_array(code, buildNativeCall("dump", 1));
            code = merge_array(code, buildNopReturn());

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

    private static void buildNative() {
        NativeFunction.declare(new NativeFunctionWrapper() {
            @Override
            public int exec(int[] params) {
                if (params.length == 1) {
                    System.out.println(params[0]);
                }

                return 0;
            }

            @Override
            public String getName() {
                return "dump";
            }

            @Override
            public boolean hasReturn() {
                return false;
            }
        });
    }
}
