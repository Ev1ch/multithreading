package counter;

public class Main {
  public static void main(String[] args) {
    final Counter counter = new Counter();
    final int operations = 100000;

    CounterThread incrementThread = new CounterThread(counter::increment, operations);
    CounterThread decrementThread = new CounterThread(counter::decrement, operations);

    incrementThread.start();
    decrementThread.start();

    try {
      incrementThread.join();
      decrementThread.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    System.out.println(counter.getCount());
  }
}
