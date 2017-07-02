package me.jimmyshaw;

import java.util.*;

class Stack<T> {
    ArrayList<T> contents = new ArrayList<T>();

    private int stackPointer = 0;

    public void push(T item) {
        contents.add(stackPointer++, item);
    }

    public T pop() {
        return contents.remove(--stackPointer);
    }

    public void pushMany(T[] items) {
        for (T item : items) {
            push(item);
        }
    }

    public int size() {
        return contents.size();
    }

    public static void main(String[] args) {
        Stack<String> myStack = new Stack<String>();
        myStack.push("A");
        String s = myStack.pop();
    }
}

class MonitorableStack<T> extends Stack<T> {
    private int highWaterMark = 0;

    public int maximumSizeSoFar() {
        return highWaterMark;
    }

    @Override
    public void push(T item) {
        if (size() > highWaterMark) {
            highWaterMark = size();
        }
        super.push(item);
    }

    // inherit pop() and pushMany()...
}







