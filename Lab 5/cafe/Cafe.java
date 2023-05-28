package cafe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.function.Consumer;

import cafe.entities.Client;
import cafe.entities.Cook;
import cafe.entities.Menu;
import cafe.entities.Order;
import cafe.utils.Randomization;

public class Cafe extends Thread {
  private static final int DEFAULT_MINIMUM_ORDERS_NUMBER = 10;
  private static final int DEFAULT_MAXIMUM_ORDERS_NUMBER = 15;
  // private static final int DEFAULT_ORDERS_NUMBER =
  // Randomization.getRandomNumber(DEFAULT_MINIMUM_ORDERS_NUMBER,
  // DEFAULT_MAXIMUM_ORDERS_NUMBER);
  private static final int DEFAULT_ORDERS_NUMBER = DEFAULT_MINIMUM_ORDERS_NUMBER;
  private static final int DEFAULT_MINIMUM_DELAY_BETWEEN_ORDER = 100;
  private static final int DEFAULT_MAXIMUM_DELAY_BETWEEN_ORDER = 200;

  private final BlockingQueue<Order> queue;
  private final Cook cook;
  private final Map<Cafe.Event, List<Consumer<Order>>> callbacks;
  private boolean isClosed;
  private final int maximumOrdersNumber;
  private final int minimumDelayBetweenOrder;
  private final int maximumDelayBetweenOrder;

  public Cafe() {
    this(
        DEFAULT_ORDERS_NUMBER,
        DEFAULT_MINIMUM_DELAY_BETWEEN_ORDER,
        DEFAULT_MAXIMUM_DELAY_BETWEEN_ORDER);
  }

  public Cafe(int maximumOrdersNumber, int minimumDelaysBetweenOrder, int maximumDelaysBetweenOrder) {
    isClosed = false;
    callbacks = new HashMap<>();
    queue = new ArrayBlockingQueue<>(maximumOrdersNumber);
    cook = new Cook();
    this.maximumOrdersNumber = maximumOrdersNumber;
    this.minimumDelayBetweenOrder = minimumDelaysBetweenOrder;
    this.maximumDelayBetweenOrder = maximumDelaysBetweenOrder;
  }

  public void close() {
    isClosed = true;
  }

  @Override
  public void run() throws RuntimeException {
    try {
      while (!isClosed) {
        var client = new Client();
        var order = new Order(client, Menu.getRandomDish());

        if (queue.size() == maximumOrdersNumber) {
          handleFailedOrder(order);
        } else {
          queue.put(order);
          cook.cookLast(queue);
          handleSuccessOrder(order);
        }

        // Thread.sleep(minimumDelayBetweenOrder);
        Thread.sleep(Randomization.getRandomNumber(minimumDelayBetweenOrder,
            maximumDelayBetweenOrder));
      }
    } catch (Exception exception) {
      throw new RuntimeException(exception);
    }
  }

  public int getQueueSize() {
    return queue.size();
  }

  public int getMaximumQueueSize() {
    return maximumOrdersNumber;
  }

  public void onFailedOrder(Consumer<Order> callback) {
    onEvent(Event.FAILED_ORDER, callback);
  }

  public void onServedOrder(Consumer<Order> callback) {
    onEvent(Event.SERVED_ORDER, callback);
  }

  private void onEvent(Event event, Consumer<Order> callback) {
    if (callbacks.containsKey(event)) {
      callbacks.get(event).add(callback);
    } else {
      var eventCallbacks = new ArrayList<Consumer<Order>>();
      eventCallbacks.add(callback);
      callbacks.put(event, eventCallbacks);
    }
  }

  private void handleFailedOrder(Order order) {
    callbacks.get(Event.FAILED_ORDER).forEach(callback -> callback.accept(order));
  }

  private void handleSuccessOrder(Order order) {
    callbacks.get(Event.SERVED_ORDER).forEach(callback -> callback.accept(order));
  }

  public static enum Event {
    FAILED_ORDER,
    SERVED_ORDER
  }
}
