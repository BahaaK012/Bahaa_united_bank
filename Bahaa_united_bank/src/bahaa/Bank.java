package bahaa;

import java.io.*;
import java.util.*;

public class Bank {

    private Account[] accounts = new Account[1000];
    private int numAccounts = 0;
    
    private String[][] employees = new String[10][2]; 
    private int numEmployees = 0;

    public void addAccount(Account acc) {
        accounts[numAccounts++] = acc;
    }

    // =========================
    // LOGIN LOGIC
    // =========================
    
    public Account[] loginUser(String username, String password) {
        int userId = -1;
        for (int i = 0; i < numAccounts; i++) {
            if (accounts[i].getUsername() != null &&
                accounts[i].getUsername().equals(username) &&
                accounts[i].checkPassword(password)) {
                userId = accounts[i].getUserId();
                break;
            }
        }

        if (userId == -1) return null;

        Account[] result = new Account[2];
        int count = 0;
        for (int i = 0; i < numAccounts; i++) {
            if (accounts[i].getUserId() == userId) {
                result[count++] = accounts[i];
            }
        }
        return (count == 2) ? result : null;
    }

    public boolean loginEmployee(String username, String password) {
        for (int i = 0; i < numEmployees; i++) {
            if (employees[i][0].equals(username) && employees[i][1].equals(password)) {
                return true;
            }
        }
        return false;
    }

    // =========================
    // FILE LOADING METHODS
    // =========================

    public void loadAccountsFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("accounts.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                int accountId = Integer.parseInt(p[0]);
                int userId = Integer.parseInt(p[1]);
                String type = p[2];

                if (type.equals("SAVINGS")) {
                    addAccount(new SavingsAccount(accountId, userId, "", ""));
                } else if (type.equals("CHECKING")) {
                    addAccount(new CheckingAccount(accountId, userId, "", ""));
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading accounts");
        }
    }

    public void loadUsersFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                int userIdFromFile = Integer.parseInt(p[0]);
                String username = p[1];
                String password = p[2];

                for (int i = 0; i < numAccounts; i++) {
                    if (accounts[i].getUserId() == userIdFromFile) {
                        accounts[i].setUsername(username);
                        accounts[i].setPassword(password);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
    }

    public void loadEmployeesFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("employees.txt"))) {
            String line;
            numEmployees = 0;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                employees[numEmployees][0] = p[1].trim(); 
                employees[numEmployees][1] = p[2].trim();
                numEmployees++;
            }
        } catch (Exception e) {
            System.out.println("Error loading employees");
        }
    }

    // =========================
    // PERSISTENT LOAN SYSTEM
    // =========================

    public void applyForLoan(int userId, double amount, String reason) {
        try (FileWriter fw = new FileWriter("loans.txt", true)) {
            // userId, amount, reason, status
            fw.write(userId + "," + amount + "," + reason + ",PENDING\n");
            System.out.println("Loan request submitted for review.");
        } catch (IOException e) {
            System.out.println("Error saving loan request.");
        }
    }

    /**
     * NEW: Allows user to see their loan history and current status
     */
    public void checkLoanStatus(int userId) {
        File file = new File("loans.txt");
        if (!file.exists()) {
            System.out.println("No loan history found.");
            return;
        }

        System.out.println("\n--- Your Loan Applications ---");
        boolean found = false;
        try (Scanner fs = new Scanner(file)) {
            while (fs.hasNextLine()) {
                String line = fs.nextLine();
                String[] p = line.split(",");
                if (p.length == 4 && Integer.parseInt(p[0]) == userId) {
                    System.out.println("Amount: $" + p[1] + " | Reason: " + p[2] + " | Status: " + p[3]);
                    found = true;
                }
            }
        } catch (Exception e) {
            System.out.println("Error reading loan status.");
        }

        if (!found) {
            System.out.println("You have not applied for any loans.");
        }
    }

    public void manageLoans() {
        List<String> loanData = new ArrayList<>();
        File file = new File("loans.txt");
        
        if (!file.exists()) {
            System.out.println("No loan requests found.");
            return;
        }

        try (Scanner fs = new Scanner(file)) {
            while (fs.hasNextLine()) {
                loanData.add(fs.nextLine());
            }
        } catch (FileNotFoundException e) { return; }

        List<String> updatedData = new ArrayList<>();
        Scanner sc = new Scanner(System.in);

        for (String line : loanData) {
            String[] p = line.split(",");
            if (p.length == 4 && p[3].equals("PENDING")) {
                System.out.println("\n--- Reviewing Loan Request ---");
                System.out.println("User ID: " + p[0] + " | Amount: $" + p[1]);
                System.out.println("Reason: " + p[2]);
                System.out.print("Action: (y) Accept, (n) Reject, (s) Skip: ");
                String action = sc.next();

                if (action.equalsIgnoreCase("y")) {
                    int uid = Integer.parseInt(p[0]);
                    double amt = Double.parseDouble(p[1]);
                    
                    boolean found = false;
                    for (int i = 0; i < numAccounts; i++) {
                        if (accounts[i].getUserId() == uid && accounts[i] instanceof SavingsAccount) {
                            accounts[i].deposit(amt);
                            found = true;
                            break;
                        }
                    }
                    
                    if (found) {
                        updatedData.add(p[0] + "," + p[1] + "," + p[2] + ",ACCEPTED");
                        System.out.println("Loan approved and funds deposited.");
                    } else {
                        System.out.println("No Savings Account found. Keeping request PENDING.");
                        updatedData.add(line);
                    }
                } else if (action.equalsIgnoreCase("n")) {
                    updatedData.add(p[0] + "," + p[1] + "," + p[2] + ",REJECTED");
                    System.out.println("Loan rejected.");
                } else {
                    updatedData.add(line); 
                }
            } else {
                updatedData.add(line); 
            }
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter("loans.txt"))) {
            for (String l : updatedData) pw.println(l);
        } catch (IOException e) {
            System.out.println("Error updating loan file.");
        }
    }
}