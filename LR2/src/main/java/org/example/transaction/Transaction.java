package org.example.transaction;

import java.util.concurrent.atomic.AtomicLong;

public class Transaction {
    private static final AtomicLong nextId = new AtomicLong(0);
    private final long id;
    private final long fromAccountId;
    private final long toAccountId;
    private final double amount;
    private final Currency currency;
    private TransactionStatus status;


    public Transaction(long fromAccountId, long toAccountId, double amount, Currency currency) {
        this.id = nextId.incrementAndGet();
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
        this.currency = currency;
        this.status = TransactionStatus.PENDING;
    }

    public long getId() {
        return id;
    }

    public long getFromAccountId() {
        return fromAccountId;
    }

    public long getToAccountId() {
        return toAccountId;
    }

    public double getAmount() {
        return amount;
    }

    public TransactionStatus getTransactionStatus() {
        return status;
    }

    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.status = transactionStatus;
    }

    @Override
    public String toString() {
        return "Transaction{" + "id=" + id +
                ", fromAccountId=" + fromAccountId + ", toAccountId=" + toAccountId +
                ", amount=" + amount + ", status=" + '}';
    }

    public Currency getCurrency() {
        return currency;
    }
}
