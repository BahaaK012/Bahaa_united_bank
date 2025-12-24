package bahaa;
public class SavingsAccount extends Account {
public SavingsAccount(int accountId, String username, String password, double initialBalance) {
    super(accountId, username, password, initialBalance);
}


	@Override 
	public boolean withdraw(double amount) {
		// I will make it 2000 but it change later based on my mood
		if(amount > 2000) {
			System.out.println("You cant do that");
			return false;
		}
		return super.withdraw(amount);
	}


}
