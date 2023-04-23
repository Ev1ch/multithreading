import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Album {
  private final SynchronousQueue<Integer> queue;
  private final Lock lock;

  public Album() {
    queue = new SynchronousQueue<>();
    lock = new ReentrantLock();
  }

  public int take() throws InterruptedException {
    return queue.take();
  }

  public void put(int value) throws InterruptedException {
    lock.lock();

    try {
      queue.put(value);
    } finally {
      lock.unlock();
    }
  }
}