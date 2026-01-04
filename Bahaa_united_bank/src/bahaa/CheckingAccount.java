package bahaa;

public class CheckingAccount extends Account {

    public CheckingAccount(int accountId, int userId, String username, String password) {
        super(accountId, userId, username, password);
    }

    @Override
    public boolean withdraw(double amount) {
        double totalToDeduct = amount;

        // here I will apply a 5% fee over withdrawals of less then 50, maybe more later 
        if (amount < 50.0) {
            double fee = amount * 0.05;
            totalToDeduct = amount + fee;
            System.out.println("Small withdrawal detected, therefore applying a 5% fee: $" + fee);
            System.out.println("Total now will be: $" + totalToDeduct);
        }

        
        return super.withdraw(totalToDeduct);
    }
}