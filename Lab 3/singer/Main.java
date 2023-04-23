import java.util.ArrayList;
import java.util.List;

public class Main {
  public static final int SINGERS_NUMBER = 2;
  public static final int FANS_NUMBER = 2;

  public static void main(String[] args) {
    Album album = new Album();
    List<Thread> threads = new ArrayList<>();

    Runnable singer = new Singer(album);
    Runnable fan = new Fan(album);

    for (int i = 0; i < SINGERS_NUMBER; i++) {
      threads.add(new Thread(singer));
    }

    for (int i = 0; i < FANS_NUMBER; i++) {
      threads.add(new Thread(fan));

    }

    for (var thread : threads) {
      thread.start();
    }

    try {
      for (var thread : threads) {
        thread.join();
      }
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}