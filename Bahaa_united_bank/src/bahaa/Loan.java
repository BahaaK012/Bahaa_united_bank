package bahaa;

public class Loan {
    private int userId;
    private double amount;
    private String status; // "PENDING", "ACCEPTED", "REJECTED"

    public Loan(int userId, double amount) {
        this.userId = userId;
        this.amount = amount;
        this.status = "PENDING";
    }

    public int getUserId() { return userId; }
    public double getAmount() { return amount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}