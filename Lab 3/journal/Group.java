import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Group {
  private String name;
  private List<Student> members;

  public Group(String name) {
    this.name = name;
    members = new ArrayList<>();
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void addMember(Student student) {
    members.add(student);
  }

  public void removeMember(Student student) {
    members.remove(student);
  }

  public List<Student> getMembers() {
    return new ArrayList<>(members);
  }

  public static Group getRandom(int size) {
    var random = new Random();
    Group group = new Group("Group " + random.nextInt());

    for (int i = 0; i < size; i++) {
      group.addMember(Student.getRandom());
    }

    return group;
  }
}