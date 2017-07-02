package me.jimmyshaw;

import java.awt.*;

abstract class Shape {
    private Color color;

    public void darken() {
        // implementation...
    }

    public void draw(Graphics g) {
        g.setColor(color);
        render(g);
    }

    abstract protected void render(Graphics g);
}

class Circle extends Shape {
    private Point center;

    private int radius;

    public Circle(Point center, int radius) {
        this.center = center;
        this.radius = radius;
    }

    public void render(Graphics g) {
        // implementation...
    }
}

class Line extends Shape {
    private int start;

    private int end;

    public Line(int start, int end) {
        this.start = start;
        this.end = end;
    }

    protected void render(Graphics g) {
        // implementation...
    }
}
