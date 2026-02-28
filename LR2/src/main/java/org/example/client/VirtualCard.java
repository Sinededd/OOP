package org.example.client;

import org.example.account.IAccount;

public class VirtualCard extends Card {
    private final String email;
    private final String cardType;

    public VirtualCard(String cardNumber, String cvv, IAccount account, String email, String cardType) {
        super(cardNumber, null, cvv, account);
        this.email = email;
        this.cardType = cardType;
    }

    public String getEmail() {
        return email;
    }

    public String getCardType() {
        return cardType;
    }

    @Override
    public String toString() {
        return "VirtualCard{" +
                "id=" + getId() +
                ", cardNumber='" + getCardNumber() + '\'' +
                ", cvv='" + getCvv() + '\'' +
                ", status=" + getStatus() +
                ", email='" + email + '\'' +
                ", cardType='" + cardType + '\'' +
                '}';
    }
}
