package org.example;

import java.util.concurrent.atomic.AtomicLong;

public class Loan {
    private static final AtomicLong nextId = new AtomicLong(0);
    private final long id;
    private long clientId;
    private double amount;
    private double interestRate;
    private int termMonths;

    public Loan(long clientId, double amount, double interestRate, int termMonths) {
        this.id = nextId.incrementAndGet();
        this.clientId = clientId;
        this.amount = amount;
        this.interestRate = interestRate;
        this.termMonths = termMonths;
    }

    public long getId() {
        return id;
    }


    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public int getTermMonths() {
        return termMonths;
    }

    public void setTermMonths(int termMonths) {
        this.termMonths = termMonths;
    }
}
