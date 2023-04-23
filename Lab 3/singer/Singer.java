public class Singer implements Runnable {
  private final Album album;

  public Singer(Album album) {
    this.album = album;
  }

  @Override
  public void run() {
    try {
      for (int i = 0; i < 100; i++) {
        album.put(i);
        System.out.println("PRODUCER: Sent value " + i);
        Thread.sleep(1000);
      }
    } catch (InterruptedException e) {
      System.out.println("ERROR: Producer.java line 14");
    }
  }
}