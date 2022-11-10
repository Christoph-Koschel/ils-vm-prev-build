package com.HexaStudio.IllusionScript.VM;

import java.util.Arrays;

public class DataList<E> {
    //Size of list
    private int size = 0;

    //Default capacity of list is 10
    private static final int DEFAULT_CAPACITY = 10;

    //This array will store all elements added to list
    private Object elements[];

    //Default constructor
    public DataList() {
        elements = new Object[DEFAULT_CAPACITY];
    }

    public DataList(int size) {
        elements = new Object[size];
    }

    //Add method
    public void add(E e) {
        if (size == elements.length) {
            ensureCapacity();
        }
        elements[size++] = e;
    }

    public void set(int i, E e) {
        if (i == elements.length) {
            ensureCapacity();
        }
        elements[i] = e;
        size = i;
    }

    //Get method
    @SuppressWarnings("unchecked")
    public E get(int i) {
        if (i > elements.length || i < 0) {
            throw new IndexOutOfBoundsException("Index: " + i + ", Size " + i);
        }
        return (E) elements[i];
    }

    //Remove method
    @SuppressWarnings("unchecked")
    public E remove(int i) {
        if (i > elements.length || i < 0) {
            throw new IndexOutOfBoundsException("Index: " + i + ", Size " + i);
        }
        Object item = elements[i];
        int numElts = elements.length - (i + 1);
        System.arraycopy(elements, i + 1, elements, i, numElts);
        size--;
        return (E) item;
    }

    //Get Size of list
    public int size() {
        return size;
    }

    public int rawSize() {
        return elements.length;
    }

    //Print method
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int i = 0; i < size; i++) {
            sb.append(elements[i].toString());
            if (i < size - 1) {
                sb.append(",");
            }
        }
        sb.append(']');
        return sb.toString();
    }

    private void ensureCapacity() {
        int newSize = elements.length * 2;
        elements = Arrays.copyOf(elements, newSize);
    }
}