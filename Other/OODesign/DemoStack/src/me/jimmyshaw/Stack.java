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

    public static void main(String[] args) {
        Stack<String> myStack = new Stack<String>();
        myStack.push("A");
        String s = myStack.pop();
    }
}
