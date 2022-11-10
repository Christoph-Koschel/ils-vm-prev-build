package com.HexaStudio.IllusionScript.VM;

import java.util.ArrayList;

public class Memory<T> {
    private T[] data;
    private final int resize;
    private int topValue;

    public Memory(int size, int resize) {
        this.data = (T[]) new Object[size];
        this.resize = resize;
        topValue = 0;
    }

    private void checkStats(boolean needsFree) {
        if (needsFree) {
            if (topValue == data.length - 1) {
                T[] newArr = (T[]) new Object[data.length + resize];
                for (int i = 0; i < data.length; i++) {
                    newArr[i] = data[i];
                }

                data = newArr;
            }
        }
    }

    public void pushAll(T... value) {
        for (T t : value) {
            push(t);
        }
    }

    public void push(T value) {
        checkStats(true);
        data[topValue] = value;
        topValue++;
    }

    public T get(int index) {
        return data[index];
    }

    public void set(int index, T value) {
        data[index] = value;
        if (index > topValue) {
            topValue = index + 1;
        }
    }

    public int size() {
        return topValue;
    }
}
