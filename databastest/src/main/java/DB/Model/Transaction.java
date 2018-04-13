package DB.Model;

import java.io.Serializable;

public class Transaction implements Serializable{

    private double amount;
    private String to;
    private String from;
    private int transactionId;

    public Transaction(double amount, String to, String from, int transactionId) {
        this.amount = amount;
        this.to = to;
        this.from = from;
        this.transactionId = transactionId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "amount=" + amount +
                ", to='" + to + '\'' +
                ", from='" + from + '\'' +
                ", transactionId=" + transactionId +
                '}';
    }
}
