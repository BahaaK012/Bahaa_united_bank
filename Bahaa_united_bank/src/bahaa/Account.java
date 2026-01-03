package bahaa;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Account implements Transferable {

    private int accountId;
    private String username;
    private String password;

    public Account(int accountId, String username, String password) {
        this.accountId = accountId;
        this.username = username;
        this.password = password;
    }

    // ✅ FIXED name
    public int getAccountId() {
        return accountId;
    }

    public String getUsername() {
        return username;
    }

    public boolean checkPassword(String pass) {
        return password.equals(pass);
    }

    // ✅ REQUIRED by Bank.loadUsersFromFile
    public void setUsername(String username) {
        this.username = username;
    }

    // ✅ REQUIRED by Bank.loadUsersFromFile
    public void setPassword(String password) {
        this.password = password;
    }

    // ✅ balance calculated from file
    public double checkBalance() {
        double balance = 0;

        try (Scanner sc = new Scanner(new File("transactions.txt"))) {
            while (sc.hasNextLine()) {
                String[] parts = sc.nextLine().split(",");

                int id = Integer.parseInt(parts[0]);
                String type = parts[1];
                double amount = Double.parseDouble(parts[2]);

                if (id == accountId) {
                    if (type.equals("DEPOSIT") || type.equals("TRANSFER_IN")) {
                        balance += amount;
                    } else if (type.equals("WITHDRAW") || type.equals("TRANSFER_OUT")) {
                        balance -= amount;
                    }
                }
            }
        } catch (Exception e) {
            // file may not exist yet
        }

        return balance;
    }

    public void deposit(double amount) {
        saveTransaction("DEPOSIT", amount);
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
    public boolean transfer(Account toAccount, double amount) {
        if (withdraw(amount)) {
            toAccount.saveTransaction("TRANSFER_IN", amount);
            saveTransaction("TRANSFER_OUT", amount);
            return true;
        }
        return false;
    }

    private void saveTransaction(String type, double amount) {
        try (FileWriter fw = new FileWriter("transactions.txt", true)) {
            fw.write(accountId + "," + type + "," + amount + "\n");
        } catch (IOException e) {
            System.out.println("Error writing transaction");
        }
    }
    public void printTransactions() {
        try (Scanner sc = new Scanner(new File("transactions.txt"))) {
            while (sc.hasNextLine()) {
                String[] parts = sc.nextLine().split(",");

                int id = Integer.parseInt(parts[0]);
                String type = parts[1];
                double amount = Double.parseDouble(parts[2]);

                if (id == accountId) {
                    System.out.println(type + " : " + amount);
                }
            }
        } catch (Exception e) {
            System.out.println("No transactions found");
        }
    }

}
