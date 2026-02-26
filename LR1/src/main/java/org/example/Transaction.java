package org.example;

import java.util.concurrent.atomic.AtomicLong;

public class Transaction {
    private static final AtomicLong nextId = new AtomicLong(0);
    private final long id;
    private final long fromAccountId;
    private final long toAccountId;
    private final double amount;

    public Transaction(long fromAccountId, long toAccountId, double amount) {
        this.id = nextId.incrementAndGet();
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
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

    @Override
    public String toString() {
        return "Transaction{" + "id=" + id +
                ", fromAccountId=" + fromAccountId + ", toAccountId=" + toAccountId +
                ", amount=" + amount + '}';
    }
}
