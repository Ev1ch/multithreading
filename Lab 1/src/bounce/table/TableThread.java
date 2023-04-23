package bounce.table;

import java.util.HashMap;
import java.util.Stack;
import java.util.UUID;

import bounce.balls.Ball;
import bounce.balls.BallThread;
import bounce.events.Handler;

public class TableThread extends Thread {
	private Table table;
	private HashMap<UUID, BallThread> threads = new HashMap<UUID, BallThread>();
	public Stack<BallThread> threadsStack = new Stack<BallThread>();

	public TableThread(Table table) {
		this.table = table;
		table.addOnBallInPoolHandler(new Handler<Ball>() {
			@Override
			public void run(Ball ball) {
				BallThread thread = threads.get(ball.getId());
				thread.end();
			}
		});
		table.addOnAddBallHandler(new Handler<Ball>() {
			@Override
			public void run(Ball ball) {
				try {
					BallThread thread = new BallThread(ball);

					thread.start();
					thread.join(5000);
					threads.put(ball.getId(), thread);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
	}

	@Override
	public void run() {
		threads.values().forEach(ballThread -> {
			ballThread.start();
		});
	}
}