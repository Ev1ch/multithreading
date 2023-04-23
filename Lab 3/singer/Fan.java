public class Fan implements Runnable {
  private final Album album;

  public Fan(Album album) {
    this.album = album;
  }

  @Override
  public void run() {
    try {
      while (true) {
        int value = album.take();
        System.out.println("CONSUMER: got value " + value);
        Thread.sleep(1000);
      }
    } catch (InterruptedException e) {
      System.out.println("ERROR: Consumer.java line 14");
    }
  }
}