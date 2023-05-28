package cafe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import cafe.entities.Statistic;

public class Monitoring extends Thread {
  private static final int DEFAULT_DELAY = 1_000;

  private final List<Callable<Statistic>> getStatistics;
  private Map<Callable<Statistic>, Integer> queuesSizesSums;
  private int iterationsNumber = 0;
  private boolean isEnded = false;
  private int delay;

  public Monitoring() {
    this(DEFAULT_DELAY);
  }

  public Monitoring(int delay) {
    this.delay = delay;
    queuesSizesSums = new HashMap<>();
    getStatistics = new ArrayList<>();
  }

  public void addResource(Callable<Statistic> getStatistic) {
    this.getStatistics.add(getStatistic);
  }

  public void end() {
    isEnded = true;
  }

  @Override
  public void run() {
    while (!isEnded) {
      try {
        iterationsNumber++;
        var statistics = new ArrayList<Statistic>();

        System.out.println("Iteration " + iterationsNumber + ":");
        System.out.println();

        for (var i = 0; i < getStatistics.size(); i++) {
          var getStatistic = getStatistics.get(i);
          var statistic = getStatistic.call();

          if (!queuesSizesSums.containsKey(getStatistic)) {
            queuesSizesSums.put(getStatistic, statistic.getQueueSize());
          } else {
            queuesSizesSums.put(getStatistic, queuesSizesSums.get(getStatistic) + statistic.getQueueSize());
          }

          var queuesSizesSum = queuesSizesSums.get(getStatistic);
          statistics.add(statistic);

          System.out.println(statistic);
          var averageQueueSize = queuesSizesSum / (float) iterationsNumber;
          System.out.println(
              "Queue size average: "
                  + averageQueueSize +
                  ", " + averageQueueSize / statistic.getMaxQueueSize() * 100 + "%");

          System.out.println();
        }

        System.out.println(Statistic.getAverage(statistics));
        System.out.println(
            "Queue size average: " + getQueueSizesAverage() + ", "
                + getQueueSizesAverage() / statistics.get(0).getMaxQueueSize() * 100 + "%");

        System.out.println("--------------------------------------------------");

        Thread.sleep(delay);
      } catch (Exception exception) {
        throw new RuntimeException(exception);
      }
    }
  }

  private float getQueueSizesAverage() {
    var queuesSizesAveragesSum = 0f;

    for (var queuesSizesSum : queuesSizesSums.values()) {
      queuesSizesAveragesSum += queuesSizesSum / (float) iterationsNumber;
    }

    return queuesSizesAveragesSum / queuesSizesSums.size();
  }
}
