package bahaa;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

public class BankTest {

    @Test
    public void testSavingsWithdrawLimit() {
     
        SavingsAccount sa = new SavingsAccount(1, 1, "user", "pass");
        sa.deposit(5000);
        boolean result = sa.withdraw(2500); 
        assertFalse( result);
    }

    @Test
    public void testCheckingFee() {
       
        CheckingAccount ca = new CheckingAccount(2, 1, "user", "pass");
        ca.deposit(100);
        ca.withdraw(20);
        assertEquals(79.0, ca.checkBalance(), 0.01);
    } 
    @Test
    public void testTransferLogic() {
        // Clear old data
        new File("transactions.txt").delete();
//   to see if I put 500 then send 200 to another it should be 300,200
        SavingsAccount fromAcc = new SavingsAccount(1, 1, "sender", "pass");
        CheckingAccount toAcc = new CheckingAccount(2, 2, "receiver", "pass");

        fromAcc.deposit(500.0);
        // Transfer 200 from Savings to Checking
        boolean result = fromAcc.transfer(toAcc, 200.0);

        assertTrue("Transfer should be successful", result);
        assertEquals(300.0, fromAcc.checkBalance(), 0.01);
        assertEquals(200.0, toAcc.checkBalance(), 0.01);
    }
    // withdraw test to see if I 50 then withdraw a 100 what will happen
    @Test
    public void testInsufficientFundsWithdrawal() {
        new File("transactions.txt").delete();

        CheckingAccount ca = new CheckingAccount(3, 3, "user", "pass");
        ca.deposit(50.0);
        
        // Try to withdraw 100 (more than balance)
        boolean result = ca.withdraw(100.0);
        
        assertFalse( result);
        assertEquals(50.0, ca.checkBalance(), 0.01); // Balance should remain unchanged
    }

    @Test
    public void testInitialBalanceIsZero() {
        // Ensure a brand new account with no transactions has 0 balance
        new File("transactions.txt").delete();

        SavingsAccount sa = new SavingsAccount(99, 99, "newbie", "pass");
        
        assertEquals( 0.0, sa.checkBalance(), 0.01);
    }
    @Test
    public void testLoanApprovalProcess() {
        // ID: 101, Amount: 5000, Reason: "Home", Income: 50000
        Loan myLoan = new Loan(101, 5000.0, "Home Improvement", 50000.0);
        
        assertEquals(5000.0, myLoan.getAmount(), 0.01);
        assertEquals(50000.0, myLoan.getAnnualIncome(), 0.01); 
        assertEquals("Pending", myLoan.getStatus());
        myLoan.setStatus("Approved");
        assertEquals("Approved", myLoan.getStatus());
    }
    @Test
    public void testTransactionToString() {
        // 1. Create a transaction
        Transaction t = new Transaction("DEPOSIT", 500.0);

        // 2. Test Getters
        assertEquals("DEPOSIT", t.getType());
        assertEquals(500.0, t.getAmount(), 0.01);

        // 3. Test your @Override toString()
        // This must match your formatting: "Transaction Details: [type | Amount: $amount]"
        String expectedOutput = "Transaction Details: [DEPOSIT | Amount: $500.00]";
        assertEquals( expectedOutput, t.toString());
    }
    
}