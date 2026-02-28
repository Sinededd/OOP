package org.example.client;

import org.example.account.Account;
import org.example.account.IAccount;
import org.example.bank.ATM;
import org.example.bank.BankBranch;
import org.example.bank.Feedback;
import org.example.transaction.Currency;
import org.example.transaction.Transaction;
import org.example.transaction.TransactionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class Client {
    private static final AtomicLong nextId = new AtomicLong(0);
    private final long id;
    private String fullName;
    private String email = "Undefined";
    private String address = "Undefined";
    private final List<IAccount> accounts = new ArrayList<>();
    private final List<Card> cards = new ArrayList<>();


    public Client(String fullName)
    {
        id = nextId.incrementAndGet();
        this.fullName = fullName;
    }

    public Client(String fullName, String email, String address) {
        id = nextId.incrementAndGet();
        this.fullName = fullName;
        this.email = email;
        this.address = address;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getId() {
        return id;
    }

    public List<IAccount> getAccounts() {
        return accounts;
    }

    public List<Card> getCards() {
        return cards;
    }

    public  void addAccount(IAccount account) {
        accounts.add(account);
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public Transaction transferMoney(IAccount fromAccount, IAccount toAccount, double amount, Currency currency) {
        TransactionManager transactionManager = TransactionManager.getInstance();
        return transactionManager.createTransaction(fromAccount, toAccount, amount, currency);
    }

    public Transaction payWithCard(Card card, IAccount toAccount, double amount, Currency currency) {
        return transferMoney(card.getAccount(), toAccount, amount, currency);
    }

    public void useATM(ATM atm, PhysicalCard card, double amount, String action) {
        if ("WITHDRAW".equals(action)) {
            atm.withdrawCash(card, amount);
        } else if ("DEPOSIT".equals(action)) {
            atm.depositCash(card, amount);
        }
    }

    public Feedback leaveFeedback(BankBranch bankBranch, String message, int rating) {
        Feedback feedback = new Feedback(this.id, message, rating);
        bankBranch.addFeedback(feedback);
        return feedback;
    }
}
