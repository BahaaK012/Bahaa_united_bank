package bahaa;

public class Transaction {
	private String type;
	private double amount;
	
	public Transaction(String type, double amount) {
		this.type = type;
		this.amount= amount;
	}
public String getType() {
	return type;
}
public double getAmount() { 
	return amount;
}
	// added a new override for this becuase I felt bad for it, it was empty
@Override
public String toString() {
    return "Transaction Details: [" + type + " | Amount: $" + String.format("%.2f", amount) + "]";
}
	
	
	
}
