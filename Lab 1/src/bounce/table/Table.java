package bounce.table;

import java.awt.Component;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import bounce.Pool;
import bounce.balls.Ball;
import bounce.balls.BallColor;
import bounce.events.Handler;
import bounce.utils.Tuple;

public class Table {
	public ArrayList<Ball> balls = new ArrayList<Ball>();
	private ArrayList<Ball> onFieldBalls = new ArrayList<Ball>();
	private ArrayList<Ball> inPoolsBalls = new ArrayList<Ball>();
	private ArrayList<Pool> pools = new ArrayList<Pool>();
	private Component canvas;
	private ArrayList<Tuple<TableEvent, Handler<Ball>>> handlers = new ArrayList<>();

	public Table(Component canvas) {
		this.canvas = canvas;

	}

	public ArrayList<Ball> getOnFieldBalls() {
		return this.onFieldBalls;
	}

	public ArrayList<Ball> getInPoolsBalls() {
		return this.inPoolsBalls;
	}

	public void addPools() {
		this.pools.add(new Pool(this.canvas, 0, 0));
		this.pools.add(new Pool(this.canvas, 0, this.canvas.getHeight() - Pool.HEIGHT));
		this.pools.add(new Pool(this.canvas, this.canvas.getWidth() - Pool.WIDTH, 0));
		this.pools.add(new Pool(this.canvas, this.canvas.getWidth() - Pool.WIDTH, this.canvas.getHeight() - Pool.HEIGHT));
		this.canvas.repaint();
	}

	public void addBall(BallColor color) {
		int x = 10;
		int y = 10;

		if (Math.random() < 0.5) {
			x = new Random().nextInt(this.canvas.getWidth());
		} else {
			y = new Random().nextInt(this.canvas.getHeight());
		}

		Ball ball = new Ball(color, this.canvas, x, y);
		ball.addOnMoveHandler(new Handler<Ball>() {
			@Override
			public void run(Ball ball) {
				for (int i = 0; i < pools.size(); i++) {
					Pool pool = pools.get(i);

					if (ball.getX() >= pool.getX() &&
							ball.getX() + ball.getWidth() <= pool.getX() + pool.WIDTH &&
							ball.getY() >= pool.getY() &&
							ball.getY() + ball.getHeight() <= pool.getY() + pool.HEIGHT) {
						onFieldBalls.removeIf(currentBall -> currentBall.getId() == ball.getId());
						inPoolsBalls.add(ball);
						canvas.repaint();
						handlers.forEach(tuple -> {
							if (tuple.x == TableEvent.BALL_IN_POOL) {

								tuple.y.run(ball);
							}
						});
					}
				}
			}
		});
		balls.add(ball);
		onFieldBalls.add(ball);
		handlers.forEach(tuple -> {
			if (tuple.x == TableEvent.ADD_BALL) {

				tuple.y.run(ball);
			}
		});
	}

	public void draw(Graphics2D g2) {
		this.onFieldBalls.forEach(ball -> {
			ball.draw(g2);
		});

		this.pools.forEach(pool -> {
			pool.draw(g2);
		});
	}

	public void addOnBallInPoolHandler(Handler<Ball> handler) {
		handlers.add(new Tuple(TableEvent.BALL_IN_POOL, handler));
	}

	public void addOnAddBallHandler(Handler<Ball> handler) {
		handlers.add(new Tuple(TableEvent.ADD_BALL, handler));
	}
}
