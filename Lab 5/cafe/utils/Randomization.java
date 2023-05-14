package cafe.utils;

import java.util.Random;

public class Randomization {
  public static Random random = new Random();

  private Randomization() {
  }

  public static int getRandomNumber(int min, int max) {
    return random.nextInt(max - min + 1) + min;
  }
}
