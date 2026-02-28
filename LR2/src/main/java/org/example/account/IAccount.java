package org.example.account;

public interface IAccount {
    long getId();
    String getAccountNumber();
    long getClientId();
    double getBalance();
    AccountStatus getStatus();

    void deposit(double amount);
    void withdraw(double amount);

    void block();
    void activate();

    boolean isActive();
}


