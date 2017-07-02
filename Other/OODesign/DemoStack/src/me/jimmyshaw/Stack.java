package me.jimmyshaw;

import java.util.*;

class Stack<T> extends ArrayList<T> {
    private int stackPointer = 0;

    public void push(T item) {
        add(stackPointer++, item);
    }

    public T pop() {
        return remove(--stackPointer);
    }

    public static void main(String[] args) {
        Stack<String> myStack = new Stack<String>();
    }
}
