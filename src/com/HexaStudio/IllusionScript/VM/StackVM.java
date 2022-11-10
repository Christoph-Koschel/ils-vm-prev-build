package com.HexaStudio.IllusionScript.VM;

import java.util.ArrayList;
import java.util.Scanner;

class StackVM {
    private int ic;
    private int sp;
    private int ssp;
    private ArrayList<Integer> memory;
    private int type;
    private int data;
    private int running;

    public StackVM() {
        final int CAPACITY = 1_000_000;

        ic = 0;
        sp = 0;
        ssp = 0;
        memory = new ArrayList<>(CAPACITY);
        type = 0;
        data = 0;
        running = 1;

        for (int i = 0; i < CAPACITY; i++) {
            memory.add(0);
        }

    }

    public void run(int entry, boolean debug) {
        ic = entry - 1;
        ssp = sp;
        sp--;

        while (running == 1) {
            fetch();
            decode();
            execute();
            if (debug) {
                Debug.nextDebugShow(this);
            }
        }
    }

    public void loadFragment(int[] program) {
        for (int i = 0; i < program.length; i++) {
            memory.set(ic + i, program[i]);
        }

        ic += program.length;
        sp = ic;
    }

    private int getType(int ins) {
        return (0xC0000000 & ins) >> 30;
    }

    private int getData(int ins) {
        return 0x3FFFFFFF & ins;
    }

    private void fetch() {
        this.ic++;
    }

    private void decode() {
        type = getType(memory.get(ic));
        if (type == -1) {
            data = -getData(-memory.get(ic));
        } else {
            data = getData(memory.get(ic));
        }
    }

    private void execute() {
        if (type == 0 || type == -1) {
            sp++;
            memory.set(sp, data);
        } else {
            doPrimitive();
        }
    }

    private void doPrimitive() {
        switch (data) {
            case 0:
                ins_halt();
                break;
            case 1:
                ins_add();
                break;
            case 2:
                ins_sub();
                break;
            case 3:
                ins_mul();
                break;
            case 4:
                ins_div();
                break;
            case 5:
                ins_invoke();
                break;
            case 6:
                ins_jump();
                break;
            case 7:
                ins_return();
                break;
            case 8:
                ins_load_reg();
                break;
            case 9:
                ins_save_reg();
                break;
            case 10:
                ins_load();
                break;
            case 11:
                ins_save();
                break;
            case 12:
                ins_swap();
                break;
            case 13:
                ins_dup();
                break;
            case 14:
                ins_invoke_native();
                break;
        }
    }

    private void ins_halt() {
        running = 0;
    }

    private void ins_add() {
        memory.set(sp - 1, memory.get(sp - 1) + memory.get(sp));
        sp--;
    }

    private void ins_sub() {
        memory.set(sp - 1, memory.get(sp - 1) - memory.get(sp));
        sp--;
    }

    private void ins_mul() {
        memory.set(sp - 1, memory.get(sp - 1) * memory.get(sp));
        sp--;
    }

    private void ins_div() {
        memory.set(sp - 1, memory.get(sp - 1) / memory.get(sp));
        sp--;
    }

    private void ins_invoke() {
        int count = memory.get(sp);
        int target = memory.get(sp - 1);
        sp -= 2;
        int[] parameters = new int[count];
        for (int i = 0; i < count; i++) {
            parameters[i] = memory.get(sp - i);
        }
        sp -= count;
        sp++;
        memory.set(sp, ic);
        sp++;
        memory.set(sp, count);
        for (int i = 0; i < count; i++) {
            sp++;
            memory.set(sp, parameters[(count - 1) - i]);
        }
        sp++;
        memory.set(sp, count);
        ic = target - 1;
    }

    private void ins_jump() {
        int target = memory.get(sp);
        sp--;
        ic = target;
    }

    private void ins_return() {
        int target = memory.get(sp);
        sp--;
        ic = target;
    }

    private void ins_load_reg() {
        switch (memory.get(sp)) {
            case 0:
                memory.set(sp, ic);
                break;
            case 1:
                memory.set(sp, sp);
                break;
            case 2:
                memory.set(sp, ssp);
                break;
        }
    }

    private void ins_save_reg() {
        switch (memory.get(sp - 1)) {
            case 0:
                ic = memory.get(sp);
                break;
            case 1:
                sp = memory.get(sp);
                break;
            case 2:
                ssp = memory.get(sp);
                break;
        }
        sp -= 2;
    }

    private void ins_load() {
        memory.set(sp, memory.get(memory.get(sp)));
    }

    private void ins_save() {
        memory.set(memory.get(sp - 1), memory.get(sp));
        sp -= 2;
    }

    private void ins_swap() {
        int first = memory.get(sp);
        int second = memory.get(sp - 1);
        memory.set(sp - 1, first);
        memory.set(sp, second);
    }

    private void ins_dup() {
        sp++;
        memory.set(sp, memory.get(sp - 1));
    }

    private void ins_invoke_native() {
        int count = memory.get(sp);
        int target = memory.get(sp - 1);
        sp -= 2;
        int[] parameters = new int[count];
        for (int i = 0; i < count; i++) {
            parameters[i] = memory.get(sp - i);
        }
        sp -= count;


        NativeFunctionWrapper func;
        try {
            func = NativeFunction.get(target);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        int res = func.exec(parameters);
        if (func.hasReturn()) {
            sp++;
            memory.set(sp, res);
        }
    }

    private static class Debug {
        private static void clearConsole() {
            for (int i = 0; i < 10; i++) {
                System.out.println();
            }
        }

        public static void nextDebugShow(StackVM vm) {
            clearConsole();
            for (int i = 0; i < vm.memory.size(); i++) {
                final int x = vm.memory.get(i);

                if (i > vm.sp) {
                    break;
                }

                String name;
                if ((name = isIns(x)) != null) {
                    System.out.print(i + ":[\u001b[36m" + name + "\u001b[0m]");
                } else {
                    System.out.print(i + ":[\u001b[36m" + x + "\u001b[0m]");
                }


                if (i == vm.sp) {
                    System.out.print("  <-- sp");
                }
                if (i == vm.ssp) {
                    System.out.print("  <-- ssp");
                }
                if (i == vm.ic) {
                    System.out.print("  <-- pc");
                }

                System.out.print("\n");
            }
            System.out.println("Press any key for next frame");
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();
        }

        private static String isIns(int x) {
            for (Instructions ins : Instructions.values()) {
                if (x == ins.value) {
                    return ins.name;
                }
            }

            return null;
        }
    }
}
