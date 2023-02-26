package bounce;

import java.util.Random;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.Color;
import java.awt.Component;

public class Pool {
    private Component canvas;
    public static final int WIDTH = 30;
    public static final int HEIGHT = 30;
    private int x = 0;
    private int y = 0;

    public Pool(Component c, int x, int y) {
        this.canvas = c;
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.gray);
        g2.fill(new Ellipse2D.Double(x, y, WIDTH, HEIGHT));
    }
    
    public int getX() {
    	return x;
    }
    
    public int getY() {
    	return y;
    }
}