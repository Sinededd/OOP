package org.example.client;

import org.example.account.Account;
import org.example.account.IAccount;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicLong;

public abstract class Card {
    private static final AtomicLong nextId = new AtomicLong(0);
    private final long id;
    private IAccount account;
    private final String cardNumber;
    private final LocalDate expirationDate;
    private final String cvv;
    private CardStatus status;

    public Card(String cardNumber, LocalDate expirationDate, String cvv, IAccount account) {
        this.id = nextId.incrementAndGet();
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
        this.account = account;
        status = CardStatus.ACTIVE;
    }

    public IAccount getAccount() {
        return account;
    }

    public void setAccountId(IAccount account) {
        this.account = account;
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

    @Override
    public String toString() {
        return "Card{" + "id=" + id + ", cardNumber='" + cardNumber + '\'' +
                ", expirationDate=" + expirationDate + ", cvv='" + cvv + '\'' +
                ", status=" + status + '}';
    }
}
