package printers;

public class PrinterThread extends Thread {
  public static final int LINES_NUMBER = 100;
  public static final int LINE_LENGTH = 50;
  private Printer printer;
  private Syncer syncer;
  private boolean permission;

  public PrinterThread(Syncer syncer, boolean permission, Printer printer) {
    this.syncer = syncer;
    this.permission = permission;
    this.printer = printer;
  }

  @Override
  public void run() {
    for (int i = 0; i < LINES_NUMBER; i++) {
      for (int j = 0; j < LINE_LENGTH; j++) {
        syncer.then(permission, new Runnable() {
          @Override
          public void run() {
            printer.print();
          }
        });
      }
    }
  }
}
