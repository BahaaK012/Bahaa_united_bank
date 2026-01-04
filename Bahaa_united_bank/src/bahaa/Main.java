package bahaa;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Bank bank = new Bank();

      
        bank.loadAccountsFromFile();
        bank.loadUsersFromFile();
        bank.loadEmployeesFromFile();

        System.out.println("--- Welcome to the Bank System ---");
        System.out.println("1 - User Login");
        System.out.println("2 - Employee Login");
        System.out.print("Choice: ");
        int userType = sc.nextInt();
        sc.nextLine(); // Clear buffer

        if (userType == 2) {
         
            System.out.print("Employee Username: ");
            String empUser = sc.nextLine();
            System.out.print("Employee Password: ");
            String empPass = sc.nextLine();

            if (bank.loginEmployee(empUser, empPass)) {
                System.out.println("\nEmployee Login Successful!");
                bank.manageLoans();
            } else {
                System.out.println("Invalid Employee Credentials.");
            }

        } else {
        
            System.out.print("Username: ");
            String u = sc.nextLine();
            System.out.print("Password: ");
            String p = sc.nextLine();

            Account[] accs = bank.loginUser(u, p);
            if (accs == null) {
                System.out.println("Login failed");
                return;
            }

            boolean exit = false;
            while (!exit) {
                System.out.println("\n--- User Menu ---");
                System.out.println("0 - Savings Account");
                System.out.println("1 - Checking Account");
                System.out.println("2 - Logout");
                System.out.print("Select account: ");
                int accChoice = sc.nextInt();

                if (accChoice == 2) {
                    exit = true;
                    continue;
                }

                Account current = accs[accChoice];
                System.out.println("\nWorking with: " + (accChoice == 0 ? "Savings" : "Checking"));
                System.out.println("1. Deposit\n2. Withdraw\n3. Transfer\n4. Transactions\n5. Balance\n6. Apply for Loan\n7. Check Loan Status\n8. Back");
                int op = sc.nextInt();

                switch (op) {
                    case 1:
                        System.out.print("Enter amount to deposit: ");
                        double d = sc.nextDouble();
                        if (current.deposit(d)) System.out.println("Deposit successful");
                        break;
                    case 2:
                        System.out.print("Enter amount to withdraw: ");
                        double w = sc.nextDouble();
                        if (current.withdraw(w)) System.out.println("Withdrawal successful");
                        break;
                    case 3:
                        System.out.print("Transfer to (0=Savings, 1=Checking): ");
                        int t = sc.nextInt();
                        System.out.print("Enter amount: ");
                        double a = sc.nextDouble();
                        if (current.transfer(accs[t], a)) System.out.println("Transfer successful");
                        break;
                    case 4:
                        current.printTransactions();
                        break;
                    case 5:
                        System.out.println("Current balance: " + current.checkBalance());
                        break;
                     
                        case 6:
                            System.out.print("Enter amount: ");
                            double lAmt = sc.nextDouble();
                            sc.nextLine(); 
                            System.out.print("Reason: ");
                            String res = sc.nextLine();
                            bank.applyForLoan(current.getUserId(), lAmt, res);
                            break;
                        case 7:
                            bank.checkLoanStatus(current.getUserId());
                            break;
                        case 8:
                            break;
                    default:
                        System.out.println("Invalid choice");
                }
            }
        }
        System.out.println("Thank you for using our bank!");
        sc.close();
    }
}