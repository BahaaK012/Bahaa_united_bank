package bahaa;

public class Loan {
    private int userId;
    private double amount;
    private String reason;
    private String status; 

    public Loan(int userId, double amount, String reason, String status) {
        this.userId = userId;
        this.amount = amount;
        this.reason = reason;
        this.status = status;
    }

    // Getters for the pirvate values
    public int getUserId() { return userId; }
    public double getAmount() { return amount; }
    public String getReason() { return reason; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

 
    @Override
    public String toString() {
        return userId + "," + amount + "," + reason + "," + status;
    }
}