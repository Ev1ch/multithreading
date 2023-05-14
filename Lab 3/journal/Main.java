import java.util.ArrayList;

public class Main {
  public static void main(String[] args) {
    try {
      var groups = new ArrayList<Group>();
      groups.add(Group.getRandom(12));
      groups.add(Group.getRandom(15));
      groups.add(Group.getRandom(20));

      Journal journal = new Journal();

      for (var group : groups) {
        journal.addGroup(group);
      }

      Teacher lecturer = new Teacher("John", "Doe");
      Assistant firstAssistant = new Assistant("Jane", "Doe");
      Assistant secondAssistant = new Assistant("Jack", "Doe");
      Assistant thirdAssistant = new Assistant("Jill", "Doe");

      for (int i = 0; i < 2; i++) {
        lecturer.addRandomMarksForGroup(journal, groups);
        firstAssistant.addRandomMarksForGroup(journal, groups);
        secondAssistant.addRandomMarksForGroup(journal, groups);
        thirdAssistant.addRandomMarksForGroup(journal, groups);
      }

      System.out.println(journal);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}