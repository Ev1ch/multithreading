package cafe.entities;

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
}
