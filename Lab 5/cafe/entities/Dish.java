package cafe.entities;

import java.util.UUID;

public class Dish {
  private final UUID id;
  private final String name;

  public Dish(String name) {
    this.id = UUID.randomUUID();
    this.name = name;
  }

  public UUID getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}
