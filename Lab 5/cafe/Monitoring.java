package cafe;

import java.util.concurrent.Callable;

import cafe.entities.Statistic;

public class Monitoring extends Thread {
  private static final int DEFAULT_DELAY = 1_000;

  private final Callable<Statistic> getStatistic;
  private int iterationsNumber = 0;
  private int queuesSizesSum = 0;
  private boolean isEnded = false;
  private int delay;

  public Monitoring(Callable<Statistic> getStatistic) {
    this(getStatistic, DEFAULT_DELAY);
  }

  public Monitoring(Callable<Statistic> getStatistic, int delay) {
    this.getStatistic = getStatistic;
    this.delay = delay;
  }

  public void end() {
    isEnded = true;
  }

  @Override
  public void run() {
    while (!isEnded) {
      try {
        var statistic = getStatistic.call();
        iterationsNumber++;
        queuesSizesSum += statistic.getQueueSize();

        System.out.println("Iteration: " + iterationsNumber + ", " + statistic.getName());
        System.out.println("Failed orders: " + statistic.getFailedOrdersNumber() +
            ", " + statistic.getOrderFailureProbability() * 100 + "%");
        System.out.println("Serviced orders: " + statistic.getServedOrdersNumber() +
            ", " + statistic.getOrderSuccessProbability() * 100 + "%");
        System.out.println(
            "Queue size: " + statistic.getQueueSize() + "/" + statistic.getMaxQueueSize() + ", average: "
                + getAverageQueueSize());
        System.out.println();

        Thread.sleep(delay);
      } catch (Exception exception) {
        throw new RuntimeException(exception);
      }
    }
  }

  private float getAverageQueueSize() {
    return queuesSizesSum / (float) iterationsNumber;
  }
}
