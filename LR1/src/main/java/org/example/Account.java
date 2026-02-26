package org.example;

import java.util.concurrent.atomic.AtomicLong;

public class Account {
    private static final AtomicLong nextId = new AtomicLong(0);
    private final long id;
    private String accountNumber;
    private long clientId;
    private double balance = 0;
    private AccountStatus status;

    public Account(String accountNumber, long clientId)
    {
        this.id = nextId.incrementAndGet();
        this.accountNumber = accountNumber;
        this.clientId = clientId;
        status = AccountStatus.ACTIVE;
    }

    public long getId() {
        return id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void changeBalance(double amount) {
        if(this.balance + amount < 0) {
            throw new IllegalArgumentException("Not enough balance");
        }
        this.balance += amount;
    }

    public void block() {
        status = AccountStatus.BLOCKED;
    }

    public void activate() {
        status = AccountStatus.ACTIVE;
    }

    public Transaction transferTo(Account targetAccount, double amount, Currency currency) {
        if(this.status != AccountStatus.ACTIVE || targetAccount.status != AccountStatus.ACTIVE) {
            throw new IllegalStateException("Both accounts must be active for transfer");
        }
        if(this.balance < amount * currency.getExchangeRateToMain()) {
            throw new IllegalArgumentException("Not enough balance for transfer");
        }
        this.changeBalance(-amount * currency.getExchangeRateToMain()); // Уменьшаем у себя
        targetAccount.changeBalance(amount * currency.getExchangeRateToMain()); // Увеличиваем у получателя

        return new Transaction(this.id, targetAccount.id, amount * currency.getExchangeRateToMain());
    }
}


