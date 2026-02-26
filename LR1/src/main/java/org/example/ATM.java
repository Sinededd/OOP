package org.example;

import java.util.concurrent.atomic.AtomicLong;

public class ATM {
    private static final AtomicLong nextId = new AtomicLong(0);
    private final long id;
    private String location;
    private double cashBalance;

    public ATM(String location, double cashBalance) {
        this.id = nextId.incrementAndGet();
        this.location = location;
        this.cashBalance = cashBalance;
    }

    public long getId() {
        return id;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getCashBalance() {
        return cashBalance;
    }

    public void setCashBalance(double cashBalance) {
        this.cashBalance = cashBalance;
    }

    public void changeCashBalance(double amount) {
        if(this.cashBalance + amount < 0) {
            throw new IllegalArgumentException("Not enough cash in ATM");
        }
        this.cashBalance += amount;
    }

    public void withdrawCash(Account account, double amount) {
        if(amount > cashBalance) {
            throw new IllegalArgumentException("Not enough cash in ATM");
        }
        account.changeBalance(-amount);
        changeCashBalance(-amount);
    }

    public void putCash(Account account, double amount) {
        account.changeBalance(amount);
        changeCashBalance(amount);
    }
}
