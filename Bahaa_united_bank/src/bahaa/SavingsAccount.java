package bahaa;

public class SavingsAccount extends Account {
	public SavingsAccount(double initialBalance) {
	    super(initialBalance);
	}
	@Override 
	public boolean withdraw(double amount) {
		// I will make it 1000 but it change later based on my mood
		if(amount > 1000) {
			System.out.println("You can not do that");
			return false;
		}
		return super.withdraw(amount);
	}


}
