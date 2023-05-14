package cafe.entities;

import java.util.UUID;

public class Order {
  private final UUID id;
  private final Client client;
  private final Dish dish;

  public Order(Client client, Dish dish) {
    this.id = UUID.randomUUID();
    this.client = client;
    this.dish = dish;
  }

  public UUID getId() {
    return id;
  }

  public Client getClient() {
    return client;
  }

  public Dish getDish() {
    return dish;
  }
}
