package bahaa;

/**
 * A type of account that can have transaction fees.
 */
public class CheckingAccount extends Account {

    /**
     * Creates a checking account by calling the base Account constructor.
     * @param accountId the unique account ID
     * @param userId the owner's user ID
     * @param username the login name
     * @param password the login password
     */
    public CheckingAccount(int accountId, int userId, String username, String password) {
        super(accountId, userId, username, password);
    }

    /**
     * Takes money out of the account. 
     * Adds a 5% fee if the withdrawal is less than $50.
     * @param amount the amount the user wants to take out
     * @return true if the withdrawal (plus any fee) was successful
     */
    @Override
    public boolean withdraw(double amount) {
        double totalToDeduct = amount;

        // Here I will apply a 5% fee for small withdrawals
        if (amount < 50.0) { 
            double fee = amount * 0.05;
            totalToDeduct = amount + fee;
            System.out.println("Small withdrawal detected, therefore applying a 5% fee: $" + fee);
            System.out.println("Total now will be: $" + totalToDeduct);
        }

        return super.withdraw(totalToDeduct);
    }
}