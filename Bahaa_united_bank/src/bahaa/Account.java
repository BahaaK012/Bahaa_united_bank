package bahaa;

public class Account implements Transferable {
    private double balance;
    private Transaction[] transactions = new Transaction[10000];// I know I should use a real arraylist I KNOW
    private int numTransactions = 0;
    
    public Account(double initialBalance) {
        this.balance = initialBalance;
    }

    public double checkBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        addTransaction(new Transaction("deposit", amount));
    }

    public boolean withdraw(double amount) {
        if(amount <= balance) {
            balance -= amount;
            addTransaction(new Transaction("withdraw", amount));
            return true;
        }
        return false;
    }

    @Override
    public boolean transfer(Account toAccount, double amount) {
        if (withdraw(amount)) {
            toAccount.deposit(amount);
            addTransaction(new Transaction("transfer to an account", amount));
            return true;
        }
        return false;
    }

	private void addTransaction(Transaction transaction) {
		if (numTransactions < transactions.length) {
			transactions[numTransactions] = transaction;
			numTransactions++; }
		
	
	}
	public void printTransactions() {
		for (int i = 0; i < numTransactions; i++) {
            System.out.println(transactions[i].getType() + ": " + transactions[i].getAmount());
		}
    
    
}

	










































}
