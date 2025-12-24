package bahaa;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Bank bank = new Bank();

        bank.loadAccountsFromFile();
        bank.loadUsersFromFile();


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

System.out.println("Choose operation:");
System.out.println("1 - Deposit");
System.out.println("2 - Withdraw");
System.out.println("3 - Transfer");
System.out.println("4 - View Transactions");

int choice1 = sc.nextInt();

switch (choice1) {

    case 1:
        System.out.print("Enter amount to deposit: ");
        double d = sc.nextDouble();
        currentAccount.deposit(d);
        System.out.println("Deposit successful");
        break;

    case 2:
        System.out.print("Enter amount to withdraw: ");
        double w = sc.nextDouble();
        if (currentAccount.withdraw(w))
            System.out.println("Withdraw successful");
        else
            System.out.println("Withdraw failed");
        break;

    case 3:
        System.out.print("Transfer to (0 or 1): ");
        int to = sc.nextInt();
        System.out.print("Enter amount: ");
        double t = sc.nextDouble();
        currentAccount.transfer(userAccounts[to], t);
        break;

    case 4:
        currentAccount.printTransactions();
        break;

    default:
        System.out.println("Invalid choice");
}

System.out.println("Current balance: " + currentAccount.checkBalance());
}
}