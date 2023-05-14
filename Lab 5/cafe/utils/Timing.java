package cafe.utils;

public class Timing {
  private Timing() {
  }

  public static void setTimeout(Runnable runnable, int delay) {
    new Thread(() -> {
      try {
        Thread.sleep(delay);
        runnable.run();
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }).start();
  }
}
