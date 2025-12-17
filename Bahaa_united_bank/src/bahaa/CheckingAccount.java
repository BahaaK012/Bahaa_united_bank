package bahaa;

public class CheckingAccount extends Account {

	public CheckingAccount(int accountId, String username, String password, double initialBalance) {
	    super(accountId, username, password, initialBalance);
	}


    @Override
    // creating a creadit limit hmm lets say 50
    public boolean withdraw(double amount) {
    	if (checkBalance() - amount < -50) {
    		System.out.println("Credit limit is reached go get a real job");
    		return false;
    	}
    	return super.withdraw(amount);
    }
    
}
