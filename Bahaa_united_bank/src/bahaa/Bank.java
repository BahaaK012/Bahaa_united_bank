package bahaa;

import java.io.*;
import java.util.*;

public class Bank {

    private Account[] accounts = new Account[1000];
    private int numAccounts = 0;
    
    // Employee data storage (Role-Based Access)
    private String[][] employees = new String[10][2]; 
    private int numEmployees = 0;
    
    // Loan management list
    private List<Loan> loanRequests = new ArrayList<>();

    public void addAccount(Account acc) {
        accounts[numAccounts++] = acc;
    }

    // =========================
    // LOGIN LOGIC (USERS & EMPLOYEES)
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
                employees[numEmployees][0] = p[1].trim(); // username
                employees[numEmployees][1] = p[2].trim(); // password
                numEmployees++;
            }
        } catch (Exception e) {
            System.out.println("Error loading employees: " + e.getMessage());
        }
    }

    // =========================
    // LOAN SYSTEM
    // =========================

    public void applyForLoan(int userId, double amount) {
        loanRequests.add(new Loan(userId, amount));
        System.out.println("Loan request submitted for user ID: " + userId);
    }

    public void manageLoans() {
        Scanner sc = new Scanner(System.in);
        Iterator<Loan> iterator = loanRequests.iterator();
        
        while (iterator.hasNext()) {
            Loan loan = iterator.next();
            if (loan.getStatus().equals("PENDING")) {
                System.out.println("\n--- Pending Loan Request ---");
                System.out.println("User ID: " + loan.getUserId());
                System.out.println("Amount: $" + loan.getAmount());
                System.out.print("Approve (y) / Reject (n) / Skip (s): ");
                String choice = sc.next();
                
                if (choice.equalsIgnoreCase("y")) {
                    loan.setStatus("ACCEPTED");
                    for (int i = 0; i < numAccounts; i++) {
                        if (accounts[i].getUserId() == loan.getUserId() && accounts[i] instanceof SavingsAccount) {
                            accounts[i].deposit(loan.getAmount());
                            System.out.println("Loan APPROVED. Funds added to Savings Account ID: " + accounts[i].getAccountId());
                        }
                    }
                } else if (choice.equalsIgnoreCase("n")) {
                    loan.setStatus("REJECTED");
                    System.out.println("Loan REJECTED.");
                }
            }
        }
    }
}