package bahaa;

import java.io.*;
import java.util.*;

/**
 * Manages the main operations of the bank, including users, employees, and loans.
 */
public class Bank {
    
    private List<Account> accounts = new ArrayList<>();
    private List<String[]> employees = new ArrayList<>();

    /**
     * Default constructor for the Bank class.
     */
    public Bank() {}

    /**
     * Adds an account to the bank's list.
     * @param acc the account to be added
     */
    public void addAccount(Account acc) {
        accounts.add(acc);
    }

    /**
     * Finds the accounts belonging to a user if the username and password match.
     * The username check is case-insensitive.
     * @param username the user's login name
     * @param password the user's login password
     * @return an array of two accounts if found, otherwise null
     */
    public Account[] loginUser(String username, String password) {
        int userId = -1; 
        for (Account acc : accounts) {
            // Updated to equalsIgnoreCase for case-insensitive usernames
            if (acc.getUsername() != null &&
                acc.getUsername().equalsIgnoreCase(username) &&
                acc.checkPassword(password)) {
                userId = acc.getUserId();
                break;
            }
        }
        if (userId == -1) return null;

        Account[] result = new Account[2];
        int count = 0;
        for (Account acc : accounts) {
            if (acc.getUserId() == userId && count < 2) {
                result[count++] = acc;
            }
        }
        return (count == 2) ? result : null;
    }

    /**
     * Checks if employee credentials are correct.
     * The username check is case-insensitive.
     * @param username the employee's username
     * @param password the employee's password
     * @return true if credentials match, false otherwise
     */
    public boolean loginEmployee(String username, String password) {
        for (String[] emp : employees) {
            // Updated to equalsIgnoreCase for case-insensitive employee names
            if (emp[0].equalsIgnoreCase(username) && emp[1].equals(password)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Loads account data from the text file.
     */
    public void loadAccountsFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("accounts.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                int accountId = Integer.parseInt(p[0].trim());
                int userId = Integer.parseInt(p[1].trim());
                String type = p[2].trim();
                
                if (type.equals("SAVINGS")) {
                    addAccount(new SavingsAccount(accountId, userId, "", ""));
                } else if (type.equals("CHECKING")) {
                    addAccount(new CheckingAccount(accountId, userId, "", ""));
                }
            }
        } catch (Exception e) { System.out.println("Error loading accounts"); }
    }

    /**
     * Links usernames and passwords from the file to the loaded accounts.
     */
    public void loadUsersFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                int userIdFromFile = Integer.parseInt(p[0].trim());
                String username = p[1].trim();
                String password = p[2].trim();
                
                for (Account acc : accounts) {
                    if (acc.getUserId() == userIdFromFile) {
                        acc.setUsername(username);
                        acc.setPassword(password);
                    }
                }
            }
        } catch (Exception e) { System.out.println("Error loading users"); }
    }

    /**
     * Loads employee login info from the file.
     */
    public void loadEmployeesFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("employees.txt"))) {
            String line;
            employees.clear(); 
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                if (p.length >= 3) {
                    employees.add(new String[]{p[1].trim(), p[2].trim()});
                }
            }
        } catch (Exception e) { System.out.println("Error loading employees"); }
    }

    /**
     * Saves a new loan application to the loans file.
     * @param userId the ID of the applying user
     * @param amount the requested loan amount
     * @param reason the purpose of the loan
     * @param income the user's yearly income
     */
    public void applyForLoan(int userId, double amount, String reason, double income) {
        try (FileWriter fw = new FileWriter("loans.txt", true)) {
            fw.write(userId + "," + amount + "," + reason + "," + income + ",PENDING\n");
            System.out.println("Loan request submitted.");
        } catch (IOException e) { System.out.println("Error saving loan."); }
    }

    /**
     * Searches the loan file to show a user their application status.
     * @param userId the ID of the user to check
     */
    public void checkLoanStatus(int userId) {
        File file = new File("loans.txt");
        if (!file.exists()) { System.out.println("No history."); return; }
        try (Scanner fs = new Scanner(file)) {
            while (fs.hasNextLine()) {
                String[] p = fs.nextLine().split(",");
                if (p.length == 5 && Integer.parseInt(p[0].trim()) == userId) {
                    System.out.println("Amount: $" + p[1] + " | Status: " + p[4]);
                }
            }
        } catch (Exception e) { System.out.println("Error checking status."); }
    }

    /**
     * Allows an employee to accept or reject pending loans.
     */
    public void manageLoans() {
        List<String> loanData = new ArrayList<>();
        File file = new File("loans.txt");
        if (!file.exists()) return;
        try (Scanner fs = new Scanner(file)) {
            while (fs.hasNextLine()) loanData.add(fs.nextLine());
        } catch (Exception e) { return; }

        List<String> updatedData = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        for (String line : loanData) {
            String[] p = line.split(",");
            if (p.length == 5 && p[4].equals("PENDING")) {
                double amt = Double.parseDouble(p[1]);
                double income = Double.parseDouble(p[3]);
                int targetId = Integer.parseInt(p[0]);
                
                System.out.println("\nReviewing Loan - Amount: $" + amt + " | Income: $" + income);
                if (amt > (income * 0.5)) System.out.println("WARNING: High Risk!");
                
                System.out.print("(y) Accept, (n) Reject, (s) Skip: ");
                String action = sc.next();
                
                if (action.equalsIgnoreCase("y")) {
                    for (Account acc : accounts) {
                        if (acc.getUserId() == targetId && acc instanceof SavingsAccount) {
                            acc.deposit(amt);
                            break;
                        }
                    }
                    updatedData.add(p[0] + "," + p[1] + "," + p[2] + "," + p[3] + ",ACCEPTED");
                } else if (action.equalsIgnoreCase("n")) {
                    updatedData.add(p[0] + "," + p[1] + "," + p[2] + "," + p[3] + ",REJECTED");
                } else {
                    updatedData.add(line);
                }
            } else {
                updatedData.add(line);
            }
        }
        try (PrintWriter pw = new PrintWriter(new FileWriter("loans.txt"))) {
            for (String l : updatedData) pw.println(l);
        } catch (Exception e) { }
    }
    
}