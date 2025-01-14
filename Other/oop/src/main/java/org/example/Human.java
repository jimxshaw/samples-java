package org.example;

import com.sun.xml.internal.bind.v2.runtime.output.StAXExStreamWriterOutput;

public class Human {
    String name;
    int age;
    int heightInInches;
    String eyeColor;

    public void speak() {
        System.out.printf("Hi I'm %s and I'm %d years old. My height is %d inches and I have %s eyes\n", name, age, heightInInches, eyeColor);
    }

    public void eat() {
        System.out.println("eating...");
    }

    public void walk() {
        System.out.println("walking...");
    }

    public void work() {
       System.out.println("working...");
    }
}
