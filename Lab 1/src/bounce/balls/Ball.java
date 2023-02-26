package bounce.balls;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;
import java.util.function.Consumer;

import bounce.events.Handler;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.Color;
import java.awt.Component;

public class Ball {
    public static final int XSIZE = 20;
    public static final int YSIZE = 20;
    private int x = 0;
    private int y = 0;
    private int dx = 10;
    private int dy = 10;
    private Component canvas;
    private UUID id = UUID.randomUUID();
    private ArrayList<Handler<Ball>> handlers = new ArrayList<Handler<Ball>>();
    private BallColor color;

    public Ball(BallColor color, Component c, int x, int y) {
        this.color = color;
        this.canvas = c;
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(color == BallColor.BLUE ? Color.blue : Color.red);
        g2.fill(new Ellipse2D.Double(x, y, XSIZE, YSIZE));
    }

    public void move() {
        x += dx;
        y += dy;

        if (x < 0) {
            x = 0;
            dx = -dx;
        }
        if (x + XSIZE >= this.canvas.getWidth()) {
            x = this.canvas.getWidth() - XSIZE;
            dx = -dx;
        }
        if (y < 0) {
            y = 0;
            dy = -dy;
        }
        if (y + YSIZE > this.canvas.getHeight()) {
            y = this.canvas.getHeight() - YSIZE;
            dy = -dy;
        }

        this.canvas.repaint();

        handlers.forEach(handler -> {
            handler.run(this);
        });
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return this.XSIZE;
    }

    public int getHeight() {
        return this.YSIZE;
    }

    public BallColor getColor() {
        return this.color;
    }

    public UUID getId() {
        return id;
    }

    public void addOnMoveHandler(Handler<Ball> handler) {
        handlers.add(handler);
    }
}