package bahaa;

public class Bank {
    private Account[] accounts = new Account[999999]; 
    private int numAccounts = 0; 

    
    public void addAccount(Account acc) {
        accounts[numAccounts] = acc;
        numAccounts++;
    }

   
    public Account getAccount(int index) {
        if(index >= 0 && index < numAccounts) {
            return accounts[index];
        }
        return null; 
    }

    
    public int getAccountsNumber() {
        return numAccounts;
    }

    
    public void printAccounts() {
        for (int i = 0; i < numAccounts; i++) {
            System.out.println("Account " + (i+1) + " Balance = " + accounts[i].checkBalance());
        }
    }
}

