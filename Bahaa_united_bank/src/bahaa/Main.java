package bahaa;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Bank bank = new Bank();
        bank.loadAccountsFromFile();
        bank.loadUsersFromFile();


        Scanner sc = new Scanner(System.in);

        System.out.print("Username: ");
        String u = sc.nextLine();

        System.out.print("Password: ");
        String p = sc.nextLine();

        Account[] accs = bank.loginUser(u, p);
        if (accs == null) {
            System.out.println("Login failed");
            return;
        }

        System.out.println("0 - Savings\n1 - Checking");
        int c = sc.nextInt();
        Account current = accs[c];

        System.out.println("1 Deposit\n2 Withdraw\n3 Transfer\n4 Transactions\n5 Balance");
        int op = sc.nextInt();

        switch (op) {

        case 1:
            System.out.print("Enter amount to deposit: ");
            double d = sc.nextDouble();
            current.deposit(d);
            System.out.println("Deposit successful");
            break;

        case 2:
            System.out.print("Enter amount to withdraw: ");
            double w = sc.nextDouble();
            if (current.withdraw(w)) {
                System.out.println("Withdrawal successful");
            } else {
                System.out.println("Withdrawal failed");
            }
            break;

        case 3:
            System.out.print("Transfer to (0 = Savings, 1 = Checking): ");
            int t = sc.nextInt();
            System.out.print("Enter amount: ");
            double a = sc.nextDouble();
            
            // Check the return value of the transfer method
            if (current.transfer(accs[t], a)) {
                System.out.println("Transfer is successful");
            } else {
                // No need for "Insufficient balance" here, 
                // because the Account class already prints it.
                System.out.println("Transfer failed");
            }
            break;

        case 4:
            current.printTransactions();
            break;

        case 5:
            System.out.println("Current balance: " + current.checkBalance());
            break;

        default:
            System.out.println("Invalid choice");
    }
    }
}
