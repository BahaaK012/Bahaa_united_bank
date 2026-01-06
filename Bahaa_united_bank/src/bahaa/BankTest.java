package bahaa;

import static org.junit.Assert.*;
import java.io.File;
import org.junit.Test;

/**
 * Tests the main functions of the bank to make sure everything works correctly
 */
public class BankTest {

    /**
     * Default constructor for BankTest
     */
    public BankTest() {
    }

    /**
     * Checks that a savings account doesn't allow withdrawing over its limit
     */
    @Test
    public void testSavingsWithdrawLimit() {
        SavingsAccount sa = new SavingsAccount(1, 1, "user", "pass");
        sa.deposit(5000);
        boolean result = sa.withdraw(2500); 
        assertFalse(result);
    }

    /**
     * Verifies that checking accounts correctly apply a fee when withdrawing
     */
    @Test
    public void testCheckingFee() {
        CheckingAccount ca = new CheckingAccount(2, 1, "user", "pass");
        ca.deposit(100);
        ca.withdraw(20);
        assertEquals(79.0, ca.checkBalance(), 0.01);
    } 

    /**
     * Tests sending money from one account to another
     */
    @Test
    public void testTransferLogic() {
        new File("transactions.txt").delete();
        SavingsAccount fromAcc = new SavingsAccount(1, 1, "sender", "pass");
        CheckingAccount toAcc = new CheckingAccount(2, 2, "receiver", "pass");

        fromAcc.deposit(500.0);
        boolean result = fromAcc.transfer(toAcc, 200.0);

        assertTrue("Transfer should be successful", result);
        assertEquals(300.0, fromAcc.checkBalance(), 0.01);
        assertEquals(200.0, toAcc.checkBalance(), 0.01);
    }

    /**
     * Ensures that users cannot withdraw more money than they actually have
     */
    @Test
    public void testInsufficientFundsWithdrawal() {
        new File("transactions.txt").delete();
        CheckingAccount ca = new CheckingAccount(3, 3, "user", "pass");
        ca.deposit(50.0);
        
        boolean result = ca.withdraw(100.0);
        
        assertFalse(result);
        assertEquals(50.0, ca.checkBalance(), 0.01);
    }

    /**
     * Confirms that a brand new account starts with a balance of zero
     */
    @Test
    public void testInitialBalanceIsZero() {
        new File("transactions.txt").delete();
        SavingsAccount sa = new SavingsAccount(99, 99, "newbie", "pass");
        assertEquals(0.0, sa.checkBalance(), 0.01);
    }

    /**
     * Tests the loan creation and the ability to update its status
     */
    @Test
    public void testLoanApprovalProcess() {
        Loan myLoan = new Loan(101, 5000.0, "Home Improvement", 50000.0);
        
        assertEquals(5000.0, myLoan.getAmount(), 0.01);
        assertEquals(50000.0, myLoan.getAnnualIncome(), 0.01); 
        assertEquals("Pending", myLoan.getStatus());
        myLoan.setStatus("Approved");
        assertEquals("Approved", myLoan.getStatus());
    }

    /**
     * Checks that the transaction text format is correct after the override
     */
    @Test
    public void testTransactionToString() {
        Transaction t = new Transaction("DEPOSIT", 500.0);

        assertEquals("DEPOSIT", t.getType());
        assertEquals(500.0, t.getAmount(), 0.01);

        String expectedOutput = "Transaction Details: [DEPOSIT | Amount: $500.00]";
        assertEquals(expectedOutput, t.toString());
    }
}