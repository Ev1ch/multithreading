public class Fan implements Runnable {
  private final Album album;

  public Fan(Album album) {
    this.album = album;
  }

  @Override
  public void run() {
    var song = album.take();

    while (song != -1) {
      song = album.take();
    }
  }
}