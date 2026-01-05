package bahaa;

public class Loan {
    private int userId;
    private double amount;
    private String reason;
    private double annualIncome;
    private String status; 

    
    public Loan(int userId, double amount, String reason, double annualIncome) {
        this.userId = userId;
        this.amount = amount;
        this.reason = reason; 
        this.annualIncome = annualIncome;
        this.status = "Pending"; // this will make pending the usual state 
    }

    // Getters
    public int getUserId() { return userId; }
    public double getAmount() { return amount; }
    public String getReason() { return reason; }
    public double getAnnualIncome() { return annualIncome; }
    public String getStatus() { return status; }
    
    public void setStatus(String status) { this.status = status; } 

    @Override
    public String toString() {
       
        return userId + "," + amount + "," + reason + "," + annualIncome + "," + status;
    }
}