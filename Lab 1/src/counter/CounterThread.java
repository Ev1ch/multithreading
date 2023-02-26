package counter;

public class CounterThread extends Thread {
  private final int operations;
  private final Runnable lambda;

  public CounterThread(Runnable lambda, int operations) {
    this.lambda = lambda;
    this.operations = operations;
  }

  @Override
  public void run() {
    for (int i = 0; i < operations; i++) {
      lambda.run();
    }
  }
}
