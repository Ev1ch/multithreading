package cafe;

import java.time.Duration;

import cafe.utils.Timing;

public class Main {
  private static final int STREETS_NUMBER = 10;
  private static final int CAFE_TIME_TO_WORK = (int) Duration.ofMinutes(1).toMillis();

  public static void main(String[] args) {
    try {
      var monitoring = new Monitoring();
      var streets = new Street[STREETS_NUMBER];

      for (int i = 0; i < STREETS_NUMBER; i++) {
        var street = new Street("Street " + i, CAFE_TIME_TO_WORK);
        streets[i] = street;
        monitoring.addResource(street::getStatistic);
      }

      for (var street : streets) {
        street.start();
        street.join();
      }
      monitoring.start();

      Timing.setTimeout(monitoring::end, CAFE_TIME_TO_WORK);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}