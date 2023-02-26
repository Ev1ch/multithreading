package printers;

public class Syncer {
  private boolean permission;
  private int runs = 0;

  public Syncer(boolean permission) {
    this.permission = permission;
  }

  public synchronized void then(boolean permission, Runnable runnable) {
    while (this.permission != permission) {
      try {
        wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    this.permission = !this.permission;
    runnable.run();
    runs++;

    if (runs % PrinterThread.LINE_LENGTH == 0) {
      System.out.println();
    }

    notifyAll();
  }
}
