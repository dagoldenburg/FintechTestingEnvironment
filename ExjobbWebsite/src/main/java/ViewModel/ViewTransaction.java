/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewModel;

/**
 *
 * @author jakdan
 */
public class ViewTransaction {
    private String to,from;
    private double amount;
    private int id;
    
    public ViewTransaction(double amount, String to, String from, int id) {
        this.amount = amount;
        this.to = to;
        this.from = from;
        this.id = id;
    }
    
    

    @Override
    public String toString() {
        return "Transaction{" +
                "amount=" + getAmount() +
                ", to='" + getTo() + '\'' +
                ", from='" + getFrom() + '\'' +
                ", transactionId=" + getId() +
                '}';
    }

    /**
     * @return the to
     */
    public String getTo() {
        return to;
    }

    /**
     * @param to the to to set
     */
    public void setTo(String to) {
        this.to = to;
    }

    /**
     * @return the from
     */
    public String getFrom() {
        return from;
    }

    /**
     * @param from the from to set
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * @return the amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
}
