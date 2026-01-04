package bahaa;

import java.io.*;
import java.util.Scanner;

public abstract class Account implements Transferable {

    protected int accountId;
    protected int userId;
    protected String username;
    protected String password;

    public Account(int accountId, int userId, String username, String password) {
        this.accountId = accountId;
        this.userId = userId;
        this.username = username;
        this.password = password;
    }

    public int getAccountId() {
        return accountId;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public boolean checkPassword(String pass) {
        if (password == null) return false;
        return password.equals(pass);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

   
    public double checkBalance() {
        double balance = 0;
        File file = new File("transactions.txt");
        if (!file.exists()) return 0;

        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty()) continue; // Skip empty lines

                String[] p = line.split(",");
                if (p.length < 3) continue; // Skip malformed lines

                int id = Integer.parseInt(p[0].trim());
                String type = p[1].trim();
                double amount = Double.parseDouble(p[2].trim());

                if (id == this.accountId) {
                    if (type.equals("DEPOSIT") || type.equals("TRANSFER_IN"))
                        balance += amount;
                    else
                        balance -= amount;
                }
            }
        } catch (Exception e) {
            // Log error so you know if parsing fails
            System.out.println("Error reading transactions: " + e.getMessage());
        }
        return balance;
    }

    public boolean deposit(double amount) {
        if (amount > 0) {
            saveTransaction("DEPOSIT", amount);
            return true; // Successful
        } else {
            System.out.println("Error DONT PUT - BEFORE YOU TYPE THE AMOUNT!.");
            return false; // Failed
        }
    }
    public boolean withdraw(double amount) {
        if (checkBalance() >= amount) {
            saveTransaction("WITHDRAW", amount);
            return true;
        }
        System.out.println("Insufficient balance");
        return false;
    }

    
    @Override
    public boolean transfer(Account to, double amount) {
        if (checkBalance() >= amount) {
            saveTransaction("TRANSFER_OUT", amount);
            to.saveTransaction("TRANSFER_IN", amount);
            return true;
        }
        System.out.println("Insufficient balance");
        return false;
    }

    protected void saveTransaction(String type, double amount) {
        try (FileWriter fw = new FileWriter("transactions.txt", true)) {
            fw.write(accountId + "," + type + "," + amount + "\n");
        } catch (IOException e) {
            System.out.println("Transaction write error");
        }
    }

    public void printTransactions() {
        try (Scanner sc = new Scanner(new File("transactions.txt"))) {
            while (sc.hasNextLine()) {
                String[] p = sc.nextLine().split(",");
                if (Integer.parseInt(p[0]) == accountId) {
                    System.out.println(p[1] + " : " + p[2]);
                }
            }
        } catch (Exception e) {
            System.out.println("No transactions");
        }
    }
}
