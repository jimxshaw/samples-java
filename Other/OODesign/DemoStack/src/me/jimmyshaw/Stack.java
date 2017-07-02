package me.jimmyshaw;

import java.util.*;

class Stack<T> {
    
    T[] contents = (T[]) new Object[1000];

    private int stackPointer = 0;

    public void push(T item) {
        assert stackPointer < contents.length : "push to full stack";
        contents[stackPointer++] = item;
    }

    public T pop() {
        assert stackPointer > 0 : "pop from empty stack";
        return contents[--stackPointer];
    }

    public void pushMany(T[] items) {
        assert (stackPointer + items.length) <= contents.length : "too many items";

        System.arraycopy(items, 0, contents, stackPointer, items.length);
        stackPointer += items.length;
    }

    public int size() {
        return contents.length;
    }

    public static void main(String[] args) {
        MonitorableStack<String> myStack = new MonitorableStack<String>();
        myStack.pushMany(new String[]{"John", "Paul", "George", "Ringo"});
        assert myStack.maximumSizeSoFar() == 4 : "Unexpected maximum size: " + myStack.maximumSizeSoFar();
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







