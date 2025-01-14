package org.example;

public class Main {
    public static void main(String[] args) {
        Human person = new Human();
        person.name = "James";
        person.age = 20;
        person.heightInInches = 80;
        person.eyeColor = "brown";

        System.out.println("Hello, World!");
        person.speak();
    }
}