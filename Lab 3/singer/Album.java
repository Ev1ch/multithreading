import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Album {
  public int producedMessagesNumber;
  public int consumedMessagesNumber;

  private final Queue<Integer> queue;
  private final Lock lock;
  private final Condition isEmpty;

  public Album() {
    queue = new LinkedList<>();
    lock = new ReentrantLock();
    isEmpty = lock.newCondition();
  }

  public int take() {
    lock.lock();

    try {
      while (queue.isEmpty()) {
        isEmpty.await();
      }

      consumedMessagesNumber++;
      isEmpty.signal();
      return queue.remove();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    } finally {
      lock.unlock();
    }
  }

  public void put(int value) {
    lock.lock();

    try {
      while (!queue.isEmpty()) {
        isEmpty.await();
      }

      queue.add(value);
      producedMessagesNumber++;
      isEmpty.signal();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    } finally {
      lock.unlock();
    }
  }
}