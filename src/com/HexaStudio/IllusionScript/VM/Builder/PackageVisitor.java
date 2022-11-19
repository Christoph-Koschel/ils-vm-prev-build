package com.HexaStudio.IllusionScript.VM.Builder;

import com.HexaStudio.IllusionScript.VM.Builder.Instructions.*;

import java.util.HashMap;
import java.util.LinkedList;

public class PackageVisitor {
    private String name;
    private HashMap<String, Integer> nativeFunctions;
    private LinkedList<FunctionVisitor> functionVisitors;

    public PackageVisitor(String name) {
        this.name = name;
        this.nativeFunctions = new HashMap<>();
        this.functionVisitors = new LinkedList<>();
    }

    public FunctionVisitor visitFunction(String name, int count) {
        FunctionVisitor functionVisitor = new FunctionVisitor(name, this, count);
        functionVisitors.add(functionVisitor);

        return functionVisitor;
    }

    public void reserveNative(String name, int i) {
        nativeFunctions.put(name, i);
    }

    public BuildResult toCode() {
        LinkedList<InstructionWrapper> bundle = new LinkedList<>();
        HashMap<String, Integer> functionStarts = new HashMap<>();
        HashMap<Label, Integer> labels = new HashMap<>();

        for (FunctionVisitor functionVisitor : functionVisitors) {
            LinkedList<InstructionWrapper> code = functionVisitor.getCode();
            functionStarts.put(functionVisitor.getName(), bundle.size());

            for (InstructionWrapper instructionWrapper : code) {
                if (instructionWrapper instanceof Label label) {
                    labels.put(label, bundle.size());
                }

                bundle.add(instructionWrapper);
            }
        }

        int[] code = new int[bundle.size()];


        for (int i = 0; i < bundle.size(); i++) {
            InstructionWrapper instructionWrapper = bundle.get(i);
            if (instructionWrapper instanceof BaseInstruction) {
                code[i] = instructionWrapper.toByte();
            } else if (instructionWrapper instanceof LabelResolve l) {
                code[i] = labels.get(l.label);
            } else if (instructionWrapper instanceof FunctionResolve p) {
                code[i] = functionStarts.get(p.target);
            } else if (instructionWrapper instanceof StaticValue) {
                code[i] = instructionWrapper.toByte();
            } else if (instructionWrapper instanceof VarStackCount v) {
                code[i] = v.toByte();
            } else if (instructionWrapper instanceof NativeFunctionResolve n) {
                code[i] = nativeFunctions.get(n.name);
            }
        }

        return new BuildResult(code, functionStarts);
    }
}
