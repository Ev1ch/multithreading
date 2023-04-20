
import java.util.Random;

public class Student extends Person {
  public Student(String firstName, String lastName) {
    super(firstName, lastName);
  }

  public static Student getRandom() {
    var random = new Random();
    return new Student("First name " + random.nextInt(), "Last name " + random.nextInt());
  }
}