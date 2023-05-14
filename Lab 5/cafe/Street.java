package cafe;

import java.time.Duration;

import cafe.entities.Order;
import cafe.entities.Statistic;
import cafe.utils.Timing;

public class Street extends Thread {
  private static final int DEFAULT_CAFE_TIME_TO_WORK = (int) Duration.ofMinutes(1).toMillis();

  private final int cafeTimeToWork;
  private final Cafe cafe;
  private final Monitoring monitoring;
  private final String name;
  private int failedOrders;
  private int servedOrders;

  public Street() {
    this("Street", DEFAULT_CAFE_TIME_TO_WORK);
  }

  public Street(String name, int cafeTimeToWork) {
    this.name = name;
    this.cafeTimeToWork = cafeTimeToWork;
    cafe = new Cafe();
    monitoring = new Monitoring(this::getStatistic);
    failedOrders = 0;
    servedOrders = 0;
  }

  @Override
  public void run() {
    cafe.onFailedOrder(this::handleFailedOrder);
    cafe.onServedOrder(this::handleServedOrder);

    cafe.start();
    monitoring.start();

    Timing.setTimeout(() -> {
      cafe.close();
      monitoring.end();
    }, cafeTimeToWork);
  }

  private Statistic getStatistic() {
    return new Statistic(name, failedOrders, servedOrders, cafe.getQueueSize(), cafe.getMaximumQueueSize());
  }

  private void handleFailedOrder(Order order) {
    failedOrders++;
  }

  private void handleServedOrder(Order order) {
    servedOrders++;
  }
}
