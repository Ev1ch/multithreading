package cafe.entities;

import java.util.Map;
import java.util.HashMap;

import cafe.utils.Randomization;

public class Menu {
  private static final Map<Dish, Float> DISHES = getDefaultDishes();

  private Menu() {
  }

  public static Map<Dish, Float> getDishes() {
    return DISHES;
  }

  public static Dish getRandomDish() {
    return DISHES
        .keySet()
        .toArray(new Dish[0])[Randomization.getRandomNumber(0, DISHES.size() - 1)];
  }

  private static Map<Dish, Float> getDefaultDishes() {
    var dishes = new HashMap<Dish, Float>();
    dishes.put(new Dish("Pizza"), 200f);
    dishes.put(new Dish("Pasta"), 150f);
    dishes.put(new Dish("Soup"), 100f);
    dishes.put(new Dish("Salad"), 120f);

    return dishes;
  }
}
