package cafe;

public class Main {
  public static void main(String[] args) {
    try {
      var street = new Street();
      street.start();
      street.join();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}