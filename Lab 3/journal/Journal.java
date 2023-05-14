import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Journal {
  public final Mark MINIMUM_MARK;
  public final Mark MAXIMUM_MARK;

  private final Lock lock;
  private final List<Group> groups;
  private final ConcurrentMap<Student, List<Mark>> map;

  public Journal() {
    MINIMUM_MARK = new Mark(0);
    MAXIMUM_MARK = new Mark(100);
    groups = new ArrayList<>();
    map = new ConcurrentHashMap<>();
    lock = new ReentrantLock();
  }

  public Journal(Mark minimumGrade, Mark maximumGrade) {
    Journal();
    MINIMUM_MARK = minimumGrade;
    MAXIMUM_MARK = maximumGrade;
  }

  public void addGroup(Group group) {
    lock.lock();

    try {
      groups.add(group);

      for (var student : group.getMembers()) {
        if (!map.containsKey(student)) {
          map.put(student, new ArrayList<>());
        }
      }
    } finally {
      lock.unlock();
    }
  }

  public void addMark(Student student, Mark mark) throws IllegalArgumentException {
    if (mark.isLessThen(MINIMUM_MARK) || mark.isBiggerThen(MAXIMUM_MARK)) {
      throw new IllegalArgumentException("Mark is out of range");
    }

    map.get(student).add(mark);
  }

  public List<Group> getGroups() {
    return new ArrayList<>(groups);
  }

  public List<Mark> getMarks(Student student) {
    return map.get(student);
  }

  public String toString() {
    String result = "";

    for (var group : groups) {
      result += group.getName() + "\n";

      for (var student : group.getMembers()) {
        result += student.getFullName() + ": " + map.get(student).toString() + "\n";
      }

      result += "\n";
    }

    return result;
  }
}