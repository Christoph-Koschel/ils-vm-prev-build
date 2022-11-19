package com.HexaStudio.IllusionScript.VM;

import com.sun.tools.attach.VirtualMachine;

public class RunInterface {
    private final int[] code;
    private final int entry;

    public RunInterface(int[] code, int entry) {
        this.code = code;
        this.entry = entry;
    }

    public void run(boolean debug) {
        StackVM vm = new StackVM();

        NativeFunction.functions.clear();
        buildNative();

        vm.loadFragment(code);
        vm.run(entry, debug);
    }


    private static void buildNative() {
        NativeFunction.declare(0, new NativeFunctionWrapper() {
            @Override
            public int exec(int[] params, StackVM vm) {
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

        NativeFunction.declare(1, new NativeFunctionWrapper() {
            @Override
            public int exec(int[] params, StackVM vm) {
                vm.setRunning(0);
                return 0;
            }

            @Override
            public String getName() {
                return "__exit";
            }

            @Override
            public boolean hasReturn() {
                return false;
            }
        });
    }
}
