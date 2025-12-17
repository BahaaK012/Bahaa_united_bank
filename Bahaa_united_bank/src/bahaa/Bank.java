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

public void depositInto(int index, double amount) {
    Account acc = getAccount(index);
    if (acc != null) {
        acc.deposit(amount);
    }
}

public void withdrawFrom(int index, double amount) {
    Account acc = getAccount(index);
    if (acc != null) {
        acc.withdraw(amount);
    }
}

public void transferBetween(int Index, int toIndex, double amount) {
    Account from = getAccount(Index);
    Account to = getAccount(toIndex);

    if (from != null && to != null) {
        from.transfer(to, amount);
    }
}


public void printTransactionsOf(int index) {
    Account acc = getAccount(index);
    if (acc != null) {
        acc.printTransactions();
    }
}

public Account[] loginUser(String username, String password) {
    Account[] result = new Account[2];
    int count = 0;

    for (int i = 0; i < numAccounts; i++) {
        if (accounts[i].getUsername().equals(username)
            && accounts[i].checkPassword(password)) {
            result[count++] = accounts[i];
        }
    }

    if (count == 2) {
        return result;
    }
    return null;
}
}


