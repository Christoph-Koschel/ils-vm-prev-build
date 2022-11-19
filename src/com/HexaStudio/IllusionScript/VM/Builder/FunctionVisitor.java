package com.HexaStudio.IllusionScript.VM.Builder;

import com.HexaStudio.IllusionScript.VM.Builder.Instructions.*;
import com.HexaStudio.IllusionScript.VM.Instructions;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class FunctionVisitor {
    private final String name;
    private PackageVisitor packageVisitor;
    private int parameterCount;
    private final LinkedList<InstructionWrapper> instructions;

    private final HashMap<Integer, Integer> visitedVariables;

    public FunctionVisitor(String name, PackageVisitor packageVisitor, int parameterCount) {
        this.name = name;
        this.packageVisitor = packageVisitor;
        this.parameterCount = parameterCount;
        this.instructions = new LinkedList<>();
        this.visitedVariables = new HashMap<>();
    }

    public void visitIns(Instructions instruction) {
        instructions.add(new BaseInstruction(instruction));
    }

    public void visitCall(Instructions instruction, String target, int count) {
        if (instruction == Instructions.INVOKE_NATIVE) {
            instructions.add(new NativeFunctionResolve(target));
        } else {
            instructions.add(new FunctionResolve(target));
        }
        instructions.add(new StaticValue(count));
        instructions.add(new BaseInstruction(instruction));
    }

    public void visitVarStackCount() {
        instructions.add(new VarStackCount(this));
    }

    public void visitInsLCD(int value) {
        instructions.add(new StaticValue(value));
    }

    public void visitLabel(Label label) {
        instructions.add(label);
    }

    public void visitJumpIns(Instructions instruction, Label label) {
        instructions.add(new LabelResolve(label));
        instructions.add(new BaseInstruction(instruction));
    }

    public int getVarOffset(int id, int size) {
        if (visitedVariables.containsKey(id)) {
            int offset = 0;

            for (Map.Entry<Integer, Integer> entry : visitedVariables.entrySet()) {
                Integer oId = entry.getKey();
                Integer oSize = entry.getValue();

                if (oId == id) {
                    return offset;
                }
                offset += oSize;
            }
        } else {
            visitedVariables.put(id, size);
            return getVarOffset(id, size);
        }

        return 0;
    }

    public int getVarStack() {
        int offset = 0;
        for (Map.Entry<Integer, Integer> entry : visitedVariables.entrySet()) {
            Integer size = entry.getValue();

            offset += size;
        }

        return offset;
    }

    public LinkedList<InstructionWrapper> getCode() {
        return instructions;
    }

    public String getName() {
        return name;
    }
}
