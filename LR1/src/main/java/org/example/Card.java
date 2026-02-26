package org.example;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicLong;

public class Card {
    private static final AtomicLong nextId = new AtomicLong(0);
    private final long id;
    private long accountId;
    private final String cardNumber;
    private final LocalDate expirationDate;
    private final String cvv;
    private CardStatus status;

    public Card(String cardNumber, LocalDate expirationDate, String cvv, long accountId) {
        this.id = nextId.incrementAndGet();
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
        this.accountId = accountId;
        status = CardStatus.ACTIVE;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public long getId() {
        return id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCvv() {
        return cvv;
    }

    public CardStatus getStatus() {
        return status;
    }

    public void block() {
        status = CardStatus.BLOCKED;
    }

    public void expire() {
        status = CardStatus.EXPIRED;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }
}
