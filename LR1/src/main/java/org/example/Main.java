package org.example;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static void main() {
        List<Transaction> transactions = new ArrayList<>();
        Currency bubleCurrency = new Currency("Б", 1.0);
        Currency eurCurrency = new Currency("EUR", 3.0);
        Currency usdCurrency = new Currency("USD", 2.5);

        Client client = new Client("Гришко Денис");
        client.setEmail("grishkodenn.g@gmail.com");

        Account denisAccount = new Account("12345678901234567890", client.getId());
        denisAccount.setBalance(1000);

        Card denisCard = new Card("4444-5555-6666-7777",
                LocalDate.now().plusYears(4),
                "123",
                denisAccount.getId());

        ATM atm = new ATM("Минск, Гикало 9", 50000.0);

        System.out.println("Баланс до снятия: " + denisAccount.getBalance());
        try {
            atm.withdrawCash(denisAccount, 200.0);
            IO.println("Баланс после снятия: " + denisAccount.getBalance());
            IO.println("Остаток в ATM: " + atm.getCashBalance() + "\n");
        } catch (Exception e) {
            IO.println("Ошибка: " + e.getMessage());
        }

        Account receiverAccount = new Account("BY99BELB3600...", 999);
        transactions.add(denisAccount.transferTo(receiverAccount, 100.0, usdCurrency));
        IO.println("Перевод 100 EUR с аккаунта Дениса на аккаунт получателя выполнен.\n");

        IO.println("Баланс Дениса: " + denisAccount.getBalance());
        IO.println("Баланс получателя: " + receiverAccount.getBalance() + "\n");


        IO.println("Транзакции:");
        for(var i : transactions) {
            IO.println(i);
        }
        IO.println();

        Feedback feedback = new Feedback(client.getId(), "Отличный сервис!", 5);
        System.out.println("Клиент " + client.getFullName() + " оставил отзыв: " + feedback.getMessage());
    }
}
