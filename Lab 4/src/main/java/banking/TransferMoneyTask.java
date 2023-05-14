package banking;

import java.util.concurrent.RecursiveAction;

class TransferMoneyTask extends RecursiveAction {
  private static final int REPS = 1000000;
  private final Bank bank;
  private final int fromAccount;
  private final int maxAmount;

  public TransferMoneyTask(Bank bank, int from, int max) {
    this.bank = bank;
    fromAccount = from;
    maxAmount = max;
  }

  @Override
  public void compute() {
    for (int i = 0; i < REPS; i++) {
      int toAccount = (int) (bank.size() * Math.random());
      int amount = (int) (maxAmount * Math.random() / REPS);
      bank.transfer(fromAccount, toAccount, amount);
    }
  }
}