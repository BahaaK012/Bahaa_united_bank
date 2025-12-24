package bahaa;
import java.io.FileWriter;
import java.io.IOException;
public class Account implements Transferable {
	private String username;
	private String password;
	private int accountId;
    private double balance;
    private Transaction[] transactions = new Transaction[10000];// I know I should use a real arraylist I KNOW
    private int numTransactions = 0;
    
    public Account(int accountId, String username, String password, double initialBalance) {
    	this.accountId = accountId;
        this.username = username;
        this.password = password;
        this.balance = initialBalance;
    }
    public int getAcoountId() {
    	   return accountId;
    }
    public String getUsername() {
        return username;
    }

    public boolean checkPassword(String pass) {
        return password.equals(pass);
    }

    public double checkBalance() {
        return balance;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void deposit(double amount) {
        balance += amount;
        addTransaction(new Transaction("deposit", amount));
    }

    public boolean withdraw(double amount) {
        if(amount <= balance) {
            balance -= amount;
            addTransaction(new Transaction("withdraw", amount));
            return true;
        }
        return false;
    }

    @Override
    public boolean transfer(Account toAccount, double amount) {
        if (withdraw(amount)) {
            toAccount.deposit(amount);
            addTransaction(new Transaction("transfer to an account", amount));
            return true;
        }
        return false;
    }

    private void addTransaction(Transaction transaction) {
        if (numTransactions < transactions.length) {
            transactions[numTransactions++] = transaction;
            saveTransactionToFile(transaction);
        }
    }
		
	
	
	public void printTransactions() {
		for (int i = 0; i < numTransactions; i++) {
            System.out.println(transactions[i].getType() + ": " + transactions[i].getAmount());
		}
    
    
}
	private void saveTransactionToFile(Transaction t) {
	    try {
	        FileWriter fw = new FileWriter("account_" + accountId + ".txt", true);
	        fw.write(t.getType() + " : " + t.getAmount() + "\n");
	        fw.close();
	    } catch (IOException e) {
	        System.out.println("Error");
	    }
	    
	       

	


}








































}
