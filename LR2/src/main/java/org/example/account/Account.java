package org.example.account;

import org.example.transaction.Currency;
import org.example.transaction.Transaction;

import java.util.concurrent.atomic.AtomicLong;

public class Account implements IAccount{
    private static final AtomicLong nextId = new AtomicLong(0);
    private final long id;
    private final String accountNumber;
    private final long clientId;
    private double balance = 0;
    private AccountStatus status;

    public Account(String accountNumber, long clientId)
    {
        this.id = nextId.incrementAndGet();
        this.accountNumber = accountNumber;
        this.clientId = clientId;
        status = AccountStatus.ACTIVE;
    }


    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getAccountNumber() {
        return accountNumber;
    }

    @Override
    public long getClientId() {
        return clientId;
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public AccountStatus getStatus() {
        return status;
    }

    @Override
    public void deposit(double amount) {
        if(status != AccountStatus.ACTIVE) {
            throw new IllegalStateException("Account is not active");
        }
        if(amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        balance += amount;
    }

    @Override
    public void withdraw(double amount) {
        if(status != AccountStatus.ACTIVE) {
            throw new IllegalStateException("Account is not active");
        }
        if(amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if(balance < amount) {
            throw new IllegalArgumentException("Not enough balance");
        }
        balance -= amount;
    }

    @Override
    public void block() {
        status = AccountStatus.BLOCKED;
    }

    @Override
    public void activate() {
        status = AccountStatus.ACTIVE;
    }

    @Override
    public boolean isActive() {
        return status == AccountStatus.ACTIVE;
    }
}
