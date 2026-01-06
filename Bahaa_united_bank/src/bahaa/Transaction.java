package bahaa;

/**
 * Represents a single money movement, like a deposit or withdrawal.
 */
public class Transaction {
	private String type;
	private double amount;
	
	/**
	 * Creates a transaction record with a type and a dollar amount.
	 * @param type the type of transaction (e.g., DEPOSIT, WITHDRAW)
	 * @param amount the dollar amount of the transaction
	 */
	public Transaction(String type, double amount) {
		this.type = type;
		this.amount= amount;
	}

	/** @return the type of transaction */
	public String getType() {
		return type;
	}

	/** @return the transaction amount */
	public double getAmount() { 
		return amount;
	}
	
	/**
	 * Changes how the transaction looks when printed to make it more readable. Also I felt bad for this class its was so useless
	 * @return a formatted string with transaction details
	 */
	@Override
	public String toString() {
	    return "Transaction Details: [" + type + " | Amount: $" + String.format("%.2f", amount) + "]";
	}
}