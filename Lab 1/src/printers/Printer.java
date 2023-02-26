package printers;

public class Printer {
  private char character;

  public Printer(char character) {
    this.character = character;
  }

  public void print() {
    System.out.print(character);
  }
}
