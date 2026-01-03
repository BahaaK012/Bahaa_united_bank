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
System.out.println("5 - View Balance");

int choice1 = sc.nextInt();

switch (choice1) {

    case 1:
        System.out.print("Amount: ");
        currentAccount.deposit(sc.nextDouble());
        break;

    case 2:
        System.out.print("Amount: ");
        currentAccount.withdraw(sc.nextDouble());
        break;

    case 3:
        System.out.print("Type account number(0,1): ");
        int to = sc.nextInt();
        System.out.print("Amount: ");
        currentAccount.transfer(bank.getAccount(to), sc.nextDouble());
        break;

    case 4:
        currentAccount.printTransactions();
        break;

    case 5:
        System.out.println("Current balance: " + currentAccount.checkBalance());
        break;

    default:
        System.out.println("Invalid choice");
}


System.out.println("Current balance: " + currentAccount.checkBalance());
}
}