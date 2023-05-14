package banking;

import java.util.ArrayList;
import java.util.concurrent.ForkJoinTask;

public class Main {
  public static final int ACCOUNTS_NUMBER = 1000;
  public static final int INITIAL_BALANCE = 10000;

  public static void main(String[] args) throws InterruptedException {
    var bank = new Bank(ACCOUNTS_NUMBER, INITIAL_BALANCE);

    var transferMoneyTasks = new ArrayList<TransferMoneyTask>();

    for (int i = 0; i < ACCOUNTS_NUMBER; i++) {
      var t = new TransferMoneyTask(bank, i, INITIAL_BALANCE);
      transferMoneyTasks.add(t);
    }

    var start = System.currentTimeMillis();
    ForkJoinTask.invokeAll(transferMoneyTasks);
    var end = System.currentTimeMillis();
    var time = end - start;

    System.out.println("Pool time: " + time);

    Bank b = new Bank(ACCOUNTS_NUMBER, INITIAL_BALANCE);
    var threads = new ArrayList<TransferMoneyThread>();

    for (int i = 0; i < ACCOUNTS_NUMBER; i++) {
      var t = new TransferMoneyThread(b, i, INITIAL_BALANCE);
      t.setPriority(Thread.NORM_PRIORITY + i % 2);
      threads.add(t);
    }

    start = System.currentTimeMillis();
    for (var thread : threads) {
      thread.start();
    }
    for (var thread : threads) {
      thread.join();
    }
    end = System.currentTimeMillis();

    time = end - start;
    System.out.println("Threads time: " + time);
  }
}