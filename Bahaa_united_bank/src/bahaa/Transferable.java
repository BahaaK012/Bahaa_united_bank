package bahaa;

/**
 * An interface that defines how money can be moved between different accounts.
 */
public interface Transferable {
    
    /**
     * Sends a specific amount of money from this account to another account.
     * @param toAccount The account receiving the money.
     * @param amount The total amount to be sent.
     * @return true if the transfer worked, false if it failed.
     */
    boolean transfer(Account toAccount, double amount);
}