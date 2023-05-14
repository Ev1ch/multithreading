public class Singer implements Runnable {
  public static final int SONGS_NUMBER = 10000;
  private final Album album;

  public Singer(Album album) {
    this.album = album;
  }

  @Override
  public void run() {
    for (int i = 1; i <= SONGS_NUMBER; i++) {
      album.put(i);
    }

    album.put(-1);
  }
}