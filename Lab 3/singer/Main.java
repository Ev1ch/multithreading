
public class Main {
  public static void main(String[] args) throws InterruptedException {
    var album = new Album();

    var singer = new Thread(new Singer(album));
    var fan = new Thread(new Fan(album));

    singer.start();
    fan.start();
    singer.join();
    fan.join();

    System.out.println("PRODUCER: " + (album.producedMessagesNumber - 1) + " songs produced");
    System.out.println("CONSUMER: " + (album.consumedMessagesNumber - 1) + " songs consumed");
  }
}