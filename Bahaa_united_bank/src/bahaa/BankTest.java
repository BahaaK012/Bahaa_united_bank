package bahaa;

import static org.junit.Assert.*;
import org.junit.Test;

public class BankTest {

    @Test
    public void testSavingsWithdrawLimit() {
     
        SavingsAccount sa = new SavingsAccount(1, 1, "user", "pass");
        sa.deposit(5000);
        boolean result = sa.withdraw(2500); 
        assertFalse("Should be false because 2500 is over the 2000 limit", result);
    }

    @Test
    public void testCheckingFee() {
       
        CheckingAccount ca = new CheckingAccount(2, 1, "user", "pass");
        ca.deposit(100);
        ca.withdraw(20); // 5% of 20 is 1.0. Total deduction = 21
        assertEquals(79.0, ca.checkBalance(), 0.01);
    }
}