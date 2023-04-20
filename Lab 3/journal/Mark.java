
public class Mark {
  private double value;

  public Mark(double value) {
    this.value = value;
  }

  public void setValue(double value) {
    this.value = value;
  }

  public double getValue() {
    return value;
  }

  public boolean isBiggerThen(Mark otherMark) {
    return value > otherMark.value;
  }

  public boolean isLessThen(Mark otherMark) {
    return value < otherMark.value;
  }

  public String toString() {
    return String.valueOf(value);
  }
}
