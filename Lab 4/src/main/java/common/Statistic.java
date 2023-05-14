package common;

import java.util.HashMap;
import java.util.Map;

public class Statistic {
  private Map<Integer, Integer> sampling;

  public Statistic(Map<Integer, Integer> sampling) {
    this.sampling = sampling;
  }

  public float getExpectedValue() {
    var probabilities = new HashMap<Integer, Float>();
    for (var sample : sampling.entrySet()) {
      probabilities.put(sample.getKey(), getProbability(sample.getKey()));
    }

    float sum = 0;
    for (var entry : probabilities.entrySet()) {
      sum += entry.getKey() * entry.getValue();
    }

    return sum;
  }

  public float getDispersion() {
    var copy = new HashMap<Integer, Integer>();
    for (var sample : sampling.entrySet()) {
      copy.put((int) Math.pow(sample.getKey(), 2), sample.getValue());
    }

    return new Statistic(copy).getExpectedValue() - (float) Math.pow(getExpectedValue(), 2);
  }

  public float getStandartError() {
    return (float) Math.sqrt(getDispersion());
  }

  private float getProbability(int value) {
    return sampling.get(value) / (float) getSamplingSize();
  }

  private int getSamplingSize() {
    int size = 0;

    for (var sample : sampling.entrySet()) {
      size += sample.getValue();
    }

    return size;
  }
}