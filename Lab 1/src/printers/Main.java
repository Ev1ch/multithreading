package printers;

public class Main {
  public static void main(String[] args) {
    Syncer syncer = new Syncer(true);

    Printer minusPrinter = new Printer('-');
    Printer dashPrinter = new Printer('|');

    PrinterThread minusPrinterThread = new PrinterThread(syncer, true, minusPrinter);
    PrinterThread dashPrinterThread = new PrinterThread(syncer, false, dashPrinter);

    minusPrinterThread.start();
    dashPrinterThread.start();
  }
}
