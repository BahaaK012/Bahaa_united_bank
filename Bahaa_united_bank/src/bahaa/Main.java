package bahaa;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Bank bank = new Bank();

        // create ONE user with TWO accounts
        bank.addAccount(new SavingsAccount(1, "bahaa", "1234", 5000));
        bank.addAccount(new CheckingAccount(1, "bahaa", "1234", 100));

        Scanner sc = new Scanner(System.in);

        System.out.print("Username: ");
        String username = sc.nextLine();

        System.out.print("Password: ");
        String password = sc.nextLine();

        Account[] userAccounts = bank.loginUser(username, password);

        if (userAccounts == null) {
            System.out.println("Login failed");
            return;
        }

        System.out.println("Login successful");
    

System.out.println("Choose account:");
System.out.println("0 - Savings");
System.out.println("1 - Checking");

int choice = sc.nextInt();
Account currentAccount = userAccounts[choice];

System.out.println("You logged into " +
    (currentAccount instanceof SavingsAccount ? "Savings" : "Checking"));
}
}