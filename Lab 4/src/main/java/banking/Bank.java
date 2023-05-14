package banking;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Bank {
  public static final int NTEST = 10000;
  private final int[] accounts;
  private long ntransacts = 0;
  private final Lock locker = new ReentrantLock();
  private final Condition notEmpty = locker.newCondition();

  public Bank(int n, int initialBalance) {
    accounts = new int[n];
    int i;
    for (i = 0; i < accounts.length; i++)
      accounts[i] = initialBalance;
    ntransacts = 0;
  }

  public void transfer(int from, int to, int amount) {
    locker.lock();

    try {
      while (accounts[from] < amount) {
        try {
          notEmpty.await();
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }

      accounts[from] -= amount;
      accounts[to] += amount;
      ntransacts++;
      notEmpty.signalAll();
      if (ntransacts % NTEST == 0)
        test();
    } finally {
      locker.unlock();
    }

  }

  public void test() {
    int sum = 0;
    for (int i = 0; i < accounts.length; i++)
      sum += accounts[i];
    // System.out.println("Transactions:" + ntransacts
    // + " Sum: " + sum);
  }

  public int size() {
    return accounts.length;
  }
}