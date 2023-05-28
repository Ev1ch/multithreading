package cafe.entities;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cafe.utils.Randomization;

public class Cook {
  private static final int DEFAULT_MINIMUM_DISHES_CAN_BE_COOKED_IN_PARALLEL = 2;
  private static final int DEFAULT_MAXIMUM_DISHES_CAN_BE_COOKED_IN_PARALLEL = 4;
  // private static final int DEFAULT_DISHES_CAN_BE_COOKED_IN_PARALLEL =
  // Randomization
  // .getRandomNumber(DEFAULT_MINIMUM_DISHES_CAN_BE_COOKED_IN_PARALLEL,
  // DEFAULT_MAXIMUM_DISHES_CAN_BE_COOKED_IN_PARALLEL);
  private static final int DEFAULT_DISHES_CAN_BE_COOKED_IN_PARALLEL = DEFAULT_MINIMUM_DISHES_CAN_BE_COOKED_IN_PARALLEL;
  private static final int DEFAULT_COOKING_TIME = 1_000;
  private static final int DEFAULT_MINIMUM_COOKING_TIME = 250;
  private static final int DEFAULT_MAXIMUM_COOKING_TIME = 500;
  private static final Map<Dish, Integer> DEFAULT_COOKING_TIMES = getDefaultCookingTimes();

  private final int defaultCookingTime;
  private final Map<Dish, Integer> cookingTimes;
  private final ExecutorService service;

  public Cook() {
    this(
        DEFAULT_DISHES_CAN_BE_COOKED_IN_PARALLEL,
        DEFAULT_COOKING_TIME,
        DEFAULT_COOKING_TIMES);
  }

  public Cook(
      int dishesCanBeCookedInParallel,
      int defaultCookingTime,
      Map<Dish, Integer> cookingTimes) {
    service = Executors.newFixedThreadPool(dishesCanBeCookedInParallel);
    this.defaultCookingTime = defaultCookingTime;
    this.cookingTimes = cookingTimes;
  }

  public int getCookingTime(Dish dish) {
    return cookingTimes.getOrDefault(dish, defaultCookingTime);
  }

  public void cookLast(BlockingQueue<Order> queue) {
    service.submit(() -> {
      try {
        var dish = queue.take().getDish();
        Thread.sleep(getCookingTime(dish));
      } catch (Exception exception) {
        exception.printStackTrace();
      }
    });
  }

  private static Map<Dish, Integer> getDefaultCookingTimes() {
    var cookingTimes = new HashMap<Dish, Integer>();

    Menu.getDishes().forEach((dish, price) -> {
      // cookingTimes.put(dish, DEFAULT_MINIMUM_COOKING_TIME);
      cookingTimes.put(dish,
          Randomization.getRandomNumber(DEFAULT_MINIMUM_COOKING_TIME,
              DEFAULT_MAXIMUM_COOKING_TIME));
    });

    return cookingTimes;
  }
}
