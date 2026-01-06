package bahaa;

/**
 * Holds data for a loan request, including the user's income and application status.
 */
public class Loan {
    private int userId;
    private double amount;
    private String reason;
    private double annualIncome;
    private String status; 

    /**
     * Creates a new loan application. The status starts as "Pending" by default.
     * @param userId the ID of the user applying
     * @param amount the loan amount requested
     * @param reason the purpose of the loan
     * @param annualIncome the user's yearly income
     */
    public Loan(int userId, double amount, String reason, double annualIncome) {
        this.userId = userId;
        this.amount = amount;
        this.reason = reason; 
        this.annualIncome = annualIncome;
        this.status = "Pending"; 
    }

    /** @return the user ID */
    public int getUserId() { return userId; }
    /** @return the loan amount */
    public double getAmount() { return amount; }
    /** @return the reason for the loan */
    public String getReason() { return reason; }
    /** @return the annual income */
    public double getAnnualIncome() { return annualIncome; }
    /** @return the current status */
    public String getStatus() { return status; }
    
    /**
     * Updates the status of the loan (e.g., to Accepted or Rejected).
     * @param status the new status string
     */
    public void setStatus(String status) { this.status = status; } 

    /**
     * Formats the loan data into a single string for saving to a file.
     * @return comma-separated loan details
     */
    @Override
    public String toString() {
        return userId + "," + amount + "," + reason + "," + annualIncome + "," + status;
    }
}