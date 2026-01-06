package bahaa;

import java.util.Scanner;

/**
 * The main entry point for the Bahaa United Bank application.
 * Tracks user mistakes and displays a scary message after 3 strikes.
 */
public class Main {

    // Global counter to track annoying behavior across all menus
    private static int wrongInputCount = 0;

    /**
     * Helper method to handle invalid non-numeric input and track strikes.
     * @param sc the Scanner object
     */
    static void handleFunnyInput(Scanner sc) {
        wrongInputCount++;
        // Message ONLY triggers on strike 3 or higher, this is meant for annoying users
        if (wrongInputCount >= 3) {
            System.out.println("\n You think you are funny huh? \nlock the doors well if that helps and sleep with your eyes open today.");
        } else {
            System.out.println("\nInvalid input. Please enter a number.");
        }
        
        if (sc.hasNext()) {
            sc.next(); 
        }
    }

    public static void showCuteGreeting() {
        String[] quotes = {
            "The best way to save money is not to spend it.' - Bahaa Bank",
            "A deposit a day keeps the bankruptcy away.",
            "Your future self will thank you for this deposit.'",
            "Your money is in safe hands!",
            "Luck follows those who manage their money well!'"
        };
        int index = (int) (Math.random() * quotes.length);
        System.out.println("\n" + quotes[index]);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Bank bank = new Bank();

        bank.loadAccountsFromFile();
        bank.loadUsersFromFile();
        bank.loadEmployeesFromFile();

        boolean authenticated = false;

        while (!authenticated) {
            System.out.println("\nWelcome to Bahaa United Bank");
            System.out.println("\nWe are not alone nor the best but your only choice");
            System.out.println("\n1 - User Login");
            System.out.println("2 - Employee Login");
            System.out.print("Choice: ");

            if (sc.hasNextInt()) {
                int userType = sc.nextInt();
                sc.nextLine(); 

                if (userType == 2) {
                    System.out.print("Employee Username: ");
                    String empUser = sc.nextLine();
                    System.out.print("Employee Password: ");
                    String empPass = sc.nextLine();

                    if (bank.loginEmployee(empUser, empPass)) {
                        System.out.println("\nEmployee Login Successful!");
                        wrongInputCount = 0; // Reset strikes when success
                        bank.manageLoans();
                        authenticated = true; 
                    } else {
                        System.out.println("Invalid credentials.");
                    }
                } else if (userType == 1) {
                    System.out.print("Username: ");
                    String u = sc.nextLine();
                    System.out.print("Password: ");
                    String p = sc.nextLine();

                    Account[] accs = bank.loginUser(u, p);
                    if (accs == null) {
                        System.out.println("Login failed.");
                    } else {
                        authenticated = true; 
                        wrongInputCount = 0; // Reset strikes again
                        showCuteGreeting();
                        handleUserMenu(sc, accs, bank);
                    }
                } else {
                    System.out.println("Invalid selection.");
                }
            } else {
                handleFunnyInput(sc);
            }
        }
        sc.close();
    }

    private static void handleUserMenu(Scanner sc, Account[] accs, Bank bank) {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n User Menu");
            System.out.println("1 - Savings Account");
            System.out.println("2 - Checking Account");
            System.out.println("3 - Exit");
            System.out.print("Select account: ");
            
            if (!sc.hasNextInt()) {
                handleFunnyInput(sc);
                continue;
            }
            
            int choice = sc.nextInt();
            // Exit is now option 3
            if (choice == 3) { exit = true; continue; }
            
            // Map 1 to index 0, 2 to index 1
            if (choice < 1 || choice > 2) { 
                System.out.println("Invalid selection. Enter 1, 2, or 3."); 
                continue; 
            }

            Account current = accs[choice - 1];
            System.out.println("\nWorking with: " + (choice == 1 ? "Savings" : "Checking"));
            System.out.println("1. Deposit\n2. Withdraw\n3. Transfer\n4. Transactions\n5. Balance\n6. Apply for Loan\n7. Check Loan Status\n8. Back");
            
            if (!sc.hasNextInt()) {
                handleFunnyInput(sc);
                continue;
            }
            
            int op = sc.nextInt();
            switch (op) {
                case 1:
                    System.out.print("Enter amount: ");
                    if (sc.hasNextDouble()) {
                        double d = sc.nextDouble();
                        if (current.deposit(d)) System.out.println("Success!");
                    } else { handleFunnyInput(sc); }
                    break;
                case 2:
                    System.out.print("Enter amount: ");
                    if (sc.hasNextDouble()) {
                        double w = sc.nextDouble();
                        if (current.withdraw(w)) System.out.println("Success!");
                    } else { handleFunnyInput(sc); }
                    break;
                case 3:
                    System.out.print("Transfer to (1=Savings, 2=Checking): ");
                    if (sc.hasNextInt()) {
                        int t = sc.nextInt();
                        if (t < 1 || t > 2) {
                            System.out.println("Invalid account.");
                            break;
                        }
                        System.out.print("Enter amount: ");
                        if (sc.hasNextDouble()) {
                            double a = sc.nextDouble();
                            if (current.transfer(accs[t - 1], a)) System.out.println("Success!");
                        } else { handleFunnyInput(sc); }
                    } else { handleFunnyInput(sc); }
                    break;
                case 4: current.printTransactions(); break;
                case 5: System.out.println("Balance: " + current.checkBalance()); break;
                case 6:
                    System.out.print("Amount: "); 
                    if (sc.hasNextDouble()) {
                        double lAmt = sc.nextDouble();
                        sc.nextLine(); 
                        System.out.print("Reason: "); String res = sc.nextLine();
                        System.out.print("Income: "); 
                        if (sc.hasNextDouble()) {
                            double income = sc.nextDouble();
                            bank.applyForLoan(current.getUserId(), lAmt, res, income); 
                        } else { handleFunnyInput(sc); }
                    } else { handleFunnyInput(sc); }
                    break;
                case 7: bank.checkLoanStatus(current.getUserId()); break;
                case 8: break;
                default: System.out.println("Invalid choice"); break;
            }
        }
    }
}