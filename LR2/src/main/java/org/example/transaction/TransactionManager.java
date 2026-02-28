package org.example.transaction;

import org.example.account.Account;
import org.example.account.IAccount;

import java.util.ArrayList;
import java.util.List;

public class TransactionManager {
    private static TransactionManager instance;
    List<Transaction> transactions;

    private TransactionManager() {
        transactions = new ArrayList<>();
    }

    public static TransactionManager getInstance() {
        if (instance == null) {
            instance = new TransactionManager();
        }
        return instance;
    }

    public Transaction createTransaction(IAccount fromAccount, IAccount toAccount, double amount, Currency currency) {
        Transaction transaction = new Transaction(fromAccount.getId(),
                toAccount.getId(), amount, currency);
        try {
            fromAccount.withdraw(amount);
            toAccount.deposit(amount);
            transaction.setTransactionStatus(TransactionStatus.COMPLETED);
        } catch (Exception e) {
            System.out.println("Transaction failed: " + e.getMessage());
            transaction.setTransactionStatus(TransactionStatus.FAILED);
        }
        transactions.add(transaction);
        return transaction;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }


}
