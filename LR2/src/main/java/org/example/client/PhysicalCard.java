package org.example.client;

import org.example.account.IAccount;

import java.time.LocalDate;

public class PhysicalCard extends Card {
    private final String cardholderName;
    private final String cardType;
    private int pin;

    public PhysicalCard(String cardNumber, LocalDate expirationDate, String cvv, IAccount account, String cardholderName, String cardType, int pin) {
        super(cardNumber, expirationDate, cvv, account);
        this.cardholderName = cardholderName;
        this.cardType = cardType;
        this.pin = pin;
    }

    public String getCardholderName() {
        return cardholderName;
    }

    public String getCardType() {
        return cardType;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    @Override
    public String toString() {
        return "PhysicalCard{" +
                "id=" + getId() +
                ", cardNumber='" + getCardNumber() + '\'' +
                ", expirationDate=" + getExpirationDate() +
                ", cvv='" + getCvv() + '\'' +
                ", status=" + getStatus() +
                ", cardholderName='" + cardholderName + '\'' +
                ", cardType='" + cardType + '\'' +
                '}';
    }
}
