package counter;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Counter {
  private final Lock lock = new ReentrantLock();

  private int count = 0;

  // private final Semaphore semaphore = new Semaphore(1);

  public void decrement() {
    lock.lock();

    try {
      count--;
    } catch (Exception e) {
    } finally {
      lock.unlock();
    }
  }

  public void increment() {
    lock.lock();

    try {
      count++;
    } catch (Exception e) {
    } finally {
      lock.unlock();
    }
  }

  public int getCount() {
    return count;
  }

  // public void decrement() {
  // try {
  // semaphore.acquire();
  // count--;
  // } catch (Exception e) {
  // } finally {
  // semaphore.release();
  // }
  // }

  // public void increment() {
  // try {
  // semaphore.acquire();
  // count++;
  // } catch (Exception e) {
  // } finally {
  // semaphore.release();
  // }
  // }

  // public int getCount() {
  // return count;
  // }

  // public synchronized void increment() {
  // count++;
  // }

  // public synchronized int getCount() {
  // return count;
  // }

  // public synchronized void decrement() {
  // count--;
  // }

  // public void increment() {
  // synchronized (this) {
  // count++;
  // }
  // }

  // public int getCount() {

  // synchronized (this) {
  // return count;
  // }
  // }

  // public void decrement() {
  // synchronized (this) {
  // count--;
  // }
  // }
}
