package bahaa;

public class CheckingAccount extends Account {

    public CheckingAccount(int accountId, String username, String password) {
        super(accountId, username, password);
    }

    @Override
    public boolean withdraw(double amount) {
        if (checkBalance() - amount < 0) {
            System.out.println("Insufficient balance");
            return false;
        }
        return super.withdraw(amount);
    }
}
