package bounce.balls;

public class BallThread extends Thread {
  private Ball b;
  private boolean isEnded;

  public BallThread(Ball ball) {
    b = ball;
  }

  @Override
  public void run() {

    int i = 0;
    try {
      while (i < 100) {
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