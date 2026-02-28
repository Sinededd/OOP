package org.example.bank;

import org.example.account.IAccount;
import org.example.client.PhysicalCard;

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

    public void changeCashBalanceATM(double amount) {
        if(this.cashBalance + amount < 0) {
            throw new IllegalArgumentException("Not enough cash in ATM");
        }
        this.cashBalance += amount;
    }

    public void withdrawCash(PhysicalCard card, double amount) {
        if(amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if(this.cashBalance < amount) {
            throw new IllegalArgumentException("Not enough cash in ATM");
        }
        IAccount account = card.getAccount();
        if(account.getStatus() != org.example.account.AccountStatus.ACTIVE) {
            throw new IllegalStateException("Account is not active");
        }
        if(account.getBalance() < amount) {
            throw new IllegalArgumentException("Not enough balance on account");
        }
        account.withdraw(amount);
        changeCashBalanceATM(-amount);
    }

    public void depositCash(PhysicalCard card, double amount) {
        if(amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        IAccount account = card.getAccount();
        if(account.getStatus() != org.example.account.AccountStatus.ACTIVE) {
            throw new IllegalStateException("Account is not active");
        }
        account.deposit(amount);
        changeCashBalanceATM(amount);
    }
}
