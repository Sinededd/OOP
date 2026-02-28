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

        Client denis = new Client("Гришко Денис", "denis@mail.com", "Лунинец");
        Client german = new Client("Василевич Герман", "german@mail.com", "Гродно");
        
        IAccount denisAccount = new Account("ACC1001", denis.getId());
        IAccount germanAccount = new Account("ACC2001", german.getId());

        denis.addAccount(denisAccount);
        german.addAccount(germanAccount);

        
        denisAccount.deposit(1000);
        System.out.println("Баланс Дениса после пополнения: " + denisAccount.getBalance());


        PhysicalCard denisCard = new PhysicalCard(
                "1111-2222-3333-4444",
                LocalDate.of(2028, 12, 31),
                "123",
                denisAccount,
                "Гришко Денис",
                "VISA",
                1234
        );

        VirtualCard germanVirtualCard = new VirtualCard(
                "9999-8888-7777-6666",
                "456",
                germanAccount,
                "german@mail.com",
                "MASTERCARD"
        );

        denis.addCard(denisCard);
        german.addCard(germanVirtualCard);

        
        ATM<PhysicalCard> atm = new ATM<PhysicalCard>("Минск, Гикало, 9", 5000);

        
        System.out.println("\nДенис снимает 200 через банкомат...");
        denis.useATM(atm, denisCard, 200, "WITHDRAW");

        System.out.println("Баланс Дениса после снятия: " + denisAccount.getBalance());
        System.out.println("Баланс банкомата: " + atm.getCashBalance());

        
        System.out.println("\nДенис переводит Герману 300...");
        Transaction transfer = denis.transferMoney(denisAccount, germanAccount, 300, usd);

        System.out.println("Статус перевода: " + transfer.getTransactionStatus());
        System.out.println("Баланс Дениса: " + denisAccount.getBalance());
        System.out.println("Баланс Германа: " + germanAccount.getBalance());

        
        System.out.println("\nДенис оплачивает картой 100 Герману...");
        Transaction payment = denis.payWithCard(denisCard, germanAccount, 100, usd);

        System.out.println("Статус оплаты: " + payment.getTransactionStatus());
        System.out.println("Баланс Дениса: " + denisAccount.getBalance());
        System.out.println("Баланс Германа: " + germanAccount.getBalance());

        
        System.out.println("\nБлокируем счет Дениса...");
        denisAccount.block();
        System.out.println("Статус счета Дениса: " + denisAccount.getStatus());

        
        System.out.println("\nПопытка перевода 50 после блокировки счета...");
        Transaction failedTransfer = denis.transferMoney(denisAccount, germanAccount, 50, usd);

        System.out.println("Статус операции: " + failedTransfer.getTransactionStatus());

        
        BankBranch branch = new BankBranch("Центральное отделение", "Главная улица 1", "+1-555-1234");

        denis.leaveFeedback(branch, "Отличное обслуживание!", 5);

        System.out.println("\nКоличество отзывов в отделении: " + branch.getFeedbacks().size());
    }
}
