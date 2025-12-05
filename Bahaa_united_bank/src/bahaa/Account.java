package bahaa;

public class Account {
	private double balance;
	
	
	public Account(double initialBalance) {
	this.balance = initialBalance;
}

public double checkBalance() {
	return balance;
}

public void deposit(double amount) {
	balance += amount;
}

public boolean withdraw(double amount) {
	if(amount <= balance ) {
		balance -= amount;
		return true;
	}
	return false;
	
	























}


















}
