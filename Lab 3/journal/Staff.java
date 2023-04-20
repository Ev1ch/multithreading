
import java.util.List;
import java.util.Random;

public class Staff extends Person {
  private String title;

  public Staff(String firstName, String lastName, String title) {
    super(firstName, lastName);
    this.title = title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getTitle() {
    return title;
  }

  public void addMark(Journal journal, Student student, Mark mark) {
    journal.addMark(student, mark);
  }

  public void addRandomMarksForGroup(Journal journal, List<Group> groups) throws InterruptedException {
    var thread = new AddRandomMarsForGroupThread(journal, groups);
    thread.start();
    thread.join();
  }
}
