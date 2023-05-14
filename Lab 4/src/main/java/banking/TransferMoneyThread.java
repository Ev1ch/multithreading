package banking;

class TransferMoneyThread extends Thread {
  private static final int REPS = 1000000;
  private final Bank bank;
  private final int fromAccount;
  private final int maxAmount;

  public TransferMoneyThread(Bank b, int from, int max) {
    bank = b;
    fromAccount = from;
    maxAmount = max;
  }

  @Override
  public void run() {
    for (int i = 0; i < REPS; i++) {
      int toAccount = (int) (bank.size() * Math.random());
      int amount = (int) (maxAmount * Math.random() / REPS);
      bank.transfer(fromAccount, toAccount, amount);
    }
  }
}