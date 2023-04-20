
import java.util.List;
import java.util.Random;

public class AddRandomMarsForGroupThread extends Thread {
  private final Journal journal;
  private final List<Group> groups;

  public AddRandomMarsForGroupThread(Journal journal, List<Group> groups) {
    this.journal = journal;
    this.groups = groups;
  }

  @Override
  public void run() {
    for (var group : groups) {
      var students = group.getMembers();

      for (var student : students) {
        journal.addMark(student, getRandomMark());
      }
    }
  }

  private Mark getRandomMark() {
    var random = new Random();
    var randomDouble = random.nextInt(
        (int) (journal.MAXIMUM_MARK.getValue() - journal.MINIMUM_MARK.getValue()))
        + (double) journal.MINIMUM_MARK.getValue();
    var randomMark = new Mark(randomDouble);

    return randomMark;
  }
}