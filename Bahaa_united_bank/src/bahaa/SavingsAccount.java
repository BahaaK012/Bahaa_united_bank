package bahaa;

public class SavingsAccount extends Account {

    public SavingsAccount(int accountId, int userId, String username, String password) {
        super(accountId, userId, username, password);
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount > 2000) {
            System.out.println("Savings limit exceeded");
            return false;
        }
        return super.withdraw(amount);
    }
}
