package counter;

public class Counter {
  private int count = 0;

  public synchronized void increment() {
    count++;
  }

  public synchronized int getCount() {
    return count;
  }

  public synchronized void decrement() {
    count--;
  }
}
