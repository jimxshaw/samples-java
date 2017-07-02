package me.jimmyshaw;

import java.awt.*;

abstract class Shape {
    private Color color;

    public void darken() {
        // implementation...
    }

    abstract public void draw(Graphics g);

    protected void setupGraphics(Graphics g) {
        g.setColor(color);
    }
}

class Circle extends Shape {
    private Point center;

    private int radius;

    public void draw(Graphics g) {
        setupGraphics(g);
        // implementation...
    }
}
