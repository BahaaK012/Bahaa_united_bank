package bahaa;
import java.io.*;
import java.util.*;

public class Bank {

    private Account[] accounts = new Account[1000];
    private int numAccounts = 0;

    public void addAccount(Account acc) {
        accounts[numAccounts++] = acc;
    }

    public Account getAccount(int index) {
        if (index >= 0 && index < numAccounts)
            return accounts[index];
        return null;
    }

    // =========================
    // LOGIN (FIXED)
    // =========================
    public Account[] loginUser(String username, String password) {

        int userId = -1;

        // 1️⃣ find userId using username/password
        for (int i = 0; i < numAccounts; i++) {
            if (accounts[i].getUsername() != null &&
                accounts[i].getUsername().equals(username) &&
                accounts[i].checkPassword(password)) {

                userId = accounts[i].getUserId();
                break;
            }
        }

        if (userId == -1)
            return null;

        // 2️⃣ collect BOTH accounts for that userId
        Account[] result = new Account[2];
        int count = 0;

        for (int i = 0; i < numAccounts; i++) {
            if (accounts[i].getUserId() == userId) {
                result[count++] = accounts[i];
            }
        }

        if (count == 2)
            return result;

        return null;
    }

    // =========================
    // LOAD ACCOUNTS
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

    // =========================
    // LOAD USERS
    // =========================
    public void loadUsersFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                int userIdFromFile = Integer.parseInt(p[0]);
                String username = p[1];
                String password = p[2];

                // Assign these credentials to EVERY account that has this userId
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

}