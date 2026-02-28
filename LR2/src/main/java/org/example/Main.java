package org.example;


import org.example.account.Account;
import org.example.account.IAccount;
import org.example.bank.ATM;
import org.example.bank.BankBranch;
import org.example.client.Client;
import org.example.client.PhysicalCard;
import org.example.client.VirtualCard;
import org.example.transaction.Currency;
import org.example.transaction.Transaction;

import java.time.LocalDate;

public class Main {
    static void main() {

        Currency usd = new Currency("USD", 1.0);

        Client alice = new Client("Алиса Иванова", "alice@mail.com", "Нью-Йорк");
        Client bob = new Client("Боб Смирнов", "bob@mail.com", "Лос-Анджелес");
        
        IAccount aliceAccount = new Account("ACC1001", alice.getId());
        IAccount bobAccount = new Account("ACC2001", bob.getId());

        alice.addAccount(aliceAccount);
        bob.addAccount(bobAccount);

        
        aliceAccount.deposit(1000);
        System.out.println("Баланс Алисы после пополнения: " + aliceAccount.getBalance());


        PhysicalCard aliceCard = new PhysicalCard(
                "1111-2222-3333-4444",
                LocalDate.of(2028, 12, 31),
                "123",
                aliceAccount,
                "Алиса Иванова",
                "VISA",
                1234
        );

        VirtualCard bobVirtualCard = new VirtualCard(
                "9999-8888-7777-6666",
                "456",
                bobAccount,
                "bob@mail.com",
                "MASTERCARD"
        );

        alice.addCard(aliceCard);
        bob.addCard(bobVirtualCard);

        
        ATM atm = new ATM("5-я Авеню", 5000);

        
        System.out.println("\nАлиса снимает 200 через банкомат...");
        alice.useATM(atm, aliceCard, 200, "WITHDRAW");

        System.out.println("Баланс Алисы после снятия: " + aliceAccount.getBalance());
        System.out.println("Баланс банкомата: " + atm.getCashBalance());

        
        System.out.println("\nАлиса переводит Бобу 300...");
        Transaction transfer = alice.transferMoney(aliceAccount, bobAccount, 300, usd);

        System.out.println("Статус перевода: " + transfer.getTransactionStatus());
        System.out.println("Баланс Алисы: " + aliceAccount.getBalance());
        System.out.println("Баланс Боба: " + bobAccount.getBalance());

        
        System.out.println("\nАлиса оплачивает картой 100 Бобу...");
        Transaction payment = alice.payWithCard(aliceCard, bobAccount, 100, usd);

        System.out.println("Статус оплаты: " + payment.getTransactionStatus());
        System.out.println("Баланс Алисы: " + aliceAccount.getBalance());
        System.out.println("Баланс Боба: " + bobAccount.getBalance());

        
        System.out.println("\nБлокируем счет Алисы...");
        aliceAccount.block();
        System.out.println("Статус счета Алисы: " + aliceAccount.getStatus());

        
        System.out.println("\nПопытка перевода 50 после блокировки счета...");
        Transaction failedTransfer = alice.transferMoney(aliceAccount, bobAccount, 50, usd);

        System.out.println("Статус операции: " + failedTransfer.getTransactionStatus());

        
        BankBranch branch = new BankBranch("Центральное отделение", "Главная улица 1", "+1-555-1234");

        alice.leaveFeedback(branch, "Отличное обслуживание!", 5);

        System.out.println("\nКоличество отзывов в отделении: " + branch.getFeedbacks().size());
    }
}
