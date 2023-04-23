package bounce.balls;

public class BallThread extends Thread {
  private Ball b;
  private boolean isEnded;

  public BallThread(Ball ball) {
    b = ball;

    setPriority(b.getColor() == BallColor.RED ? Thread.MAX_PRIORITY
        : Thread.MIN_PRIORITY);
  }

  @Override
  public void run() {

    int i = 0;
    try {
      while (i < 100) {
        // while (!isEnded) {
        b.move();
        System.out.println("Thread name = "
            + Thread.currentThread().getName());
        sleep(10);
        i++;
      }
    } catch (InterruptedException ex) {

    }
  }

  public void end() {
    isEnded = true;
  }
}