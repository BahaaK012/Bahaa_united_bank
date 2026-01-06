package bahaa;

/**
 * A type of bank account designed for saving money with a strict withdrawal limit.
 */
public class SavingsAccount extends Account {

    /**
     * Creates a new savings account using the base Account details.
     * @param accountId the unique account ID
     * @param userId the owner's user ID
     * @param username the login name
     * @param password the login password
     */
    public SavingsAccount(int accountId, int userId, String username, String password) {
        super(accountId, userId, username, password);
    }

    /**
     * Takes money out of the account, but only if the amount is $2000 or less OTHERWISE NO.
     * @param amount the amount to withdraw
     * @return true if the amount is within the limit and balance is sufficient
     */
    @Override
    public boolean withdraw(double amount) {
        if (amount > 2000) {
            System.out.println("Savings limit exceeded");
            return false;
        }
        return super.withdraw(amount); 
    }
}