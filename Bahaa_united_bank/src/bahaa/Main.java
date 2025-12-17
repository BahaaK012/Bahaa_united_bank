package bahaa;
import java.util.Scanner;
public class Main {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
		System.out.println("Welcome to Bahaa United Bank. Here we give our clients the best exp");
		Bank bank = new Bank();
		bank.addAccount(new SavingsAccount(1, "bahaa", "1234", 5000));
		bank.addAccount(new CheckingAccount(2, "ali", "abcd", 100));
	        System.out.println("Choose your account: ");
	        System.out.println("0 - Savings Account");
	        System.out.println("1 - Checking Account");
	        int accIndex = input.nextInt();

	        Account userAcc = bank.getAccount(accIndex);

	        System.out.println("You logged into account #" + accIndex);

	        // --- MENU ---
	        System.out.println("Choose an operation:");
	        System.out.println("1 - Deposit");
	        System.out.println("2 - Withdraw");
	        System.out.println("3 - Transfer");
	        System.out.println("4 - View Transactions");

	        int choice = input.nextInt();

	        switch(choice) {

	            case 1:
	                System.out.print("Enter amount to deposit: ");
	                double d = input.nextDouble();
	                userAcc.deposit(d);
	                break;

	            case 2:
	                System.out.print("Enter amount to withdraw: ");
	                double w = input.nextDouble();
	                userAcc.withdraw(w);
	                break;

	            case 3:
	                System.out.print("Transfer to which account? ");
	                int toIndex = input.nextInt();
	                System.out.print("Enter amount: ");
	                double t = input.nextDouble();
	                userAcc.transfer(bank.getAccount(toIndex), t);
	                break;

	            case 4:
	                System.out.println("Transaction history:");
	                userAcc.printTransactions();
	                break;

	            default:
	                System.out.println("Invalid choice.");
	        }

	   

	        bank.printAccounts();
	    }
	
	}


