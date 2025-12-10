package bahaa;

public class Main {

	public static void main(String[] args) {
		
		System.out.println("Welcome to Bahaa United Bank. Here we give our clients the best exp");
		Bank bank = new Bank();
		 bank.addAccount(new SavingsAccount(5000));
	        bank.addAccount(new CheckingAccount(100));

	        bank.depositInto(0, 0);
	        bank.withdrawFrom(1, 0);
	        bank.transferBetween(0, 1, 1200);

	        bank.printAccounts();
	    }
	
	}


