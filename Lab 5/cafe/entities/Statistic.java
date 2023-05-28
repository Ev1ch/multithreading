package cafe.entities;

import java.util.List;

public class Statistic {
  private final String name;
  private final int failedOrdersNumber;
  private final int servedOrdersNumber;
  private final int queueSize;
  private final int maxQueueSize;

  public Statistic(String name, int failedOrdersNumber, int servedOrdersNumber, int queueSize, int maxQueueSize) {
    this.name = name;
    this.failedOrdersNumber = failedOrdersNumber;
    this.servedOrdersNumber = servedOrdersNumber;
    this.queueSize = queueSize;
    this.maxQueueSize = maxQueueSize;
  }

  public int getFailedOrdersNumber() {
    return failedOrdersNumber;
  }

  public int getServedOrdersNumber() {
    return servedOrdersNumber;
  }

  public int getTotalOrdersNumber() {
    return failedOrdersNumber + servedOrdersNumber;
  }

  public int getQueueSize() {
    return queueSize;
  }

  public int getMaxQueueSize() {
    return maxQueueSize;
  }

  public String getName() {
    return name;
  }

  public float getOrderFailureProbability() {
    var totalOrdersNumber = getTotalOrdersNumber();

    if (totalOrdersNumber == 0) {
      return 0;
    }

    return failedOrdersNumber / (float) totalOrdersNumber;
  }

  public float getOrderSuccessProbability() {
    var totalOrdersNumber = getTotalOrdersNumber();

    if (totalOrdersNumber == 0) {
      return 0;
    }

    return servedOrdersNumber / (float) totalOrdersNumber;
  }

  public float getQueueFillFactor() {
    return queueSize / (float) maxQueueSize;
  }

  @Override
  public String toString() {
    return "Statistic: " + name + "\n" +
        "Failed orders: " + failedOrdersNumber + ", " + getOrderFailureProbability() * 100 + "%\n" +
        "Served orders: " + servedOrdersNumber + ", " + getOrderSuccessProbability() * 100 + "%\n" +
        "Queue size: " + queueSize + "/" + maxQueueSize + ", " + getQueueFillFactor() * 100 + "%";
  }

  public static Statistic getAverage(List<Statistic> statistics) {
    var maxQueueSize = statistics.get(0).getMaxQueueSize();
    var failedOrdersNumber = 0;
    var servedOrdersNumber = 0;
    var queueSize = 0;

    for (var statistic : statistics) {
      failedOrdersNumber += statistic.getFailedOrdersNumber();
      servedOrdersNumber += statistic.getServedOrdersNumber();
      queueSize += statistic.getQueueSize();
    }

    return new Statistic(
        "Average",
        (int) Math.round(failedOrdersNumber / (double) statistics.size()),
        (int) Math.round(servedOrdersNumber / (double) statistics.size()),
        (int) Math.round(queueSize / (double) statistics.size()),
        maxQueueSize);
  }
}
