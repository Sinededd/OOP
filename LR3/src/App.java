import java.util.List;

import card.GoldCard;
import infrastructure.*;
import models.Address;
import models.Item;
import models.Order;
import order.OrderPremiumType;
import staff.HumanManager;
import staff.RobotPacker;
import staff.Warehouse;
import staff.Worker;

/**
 * Точка входа в приложение.
 */
public class App {
    public static void main(String[] args) {
        // 1. Создание заказа
        var order = new Order(
                "ORD-256-X",
                new OrderPremiumType(),
                new GoldCard(),
                List.of(
                        new Item("1", "Thermal Clips", 1500),
                        new Item("2", "UNATCO Pass Card", 50)),
                "jeevacation@gmail.com",
                new Address("Agartha", "33 Thomas Street", "[REDACTED]"));

        // 2. Инициализация процессора
        NotificationManager notificationManager = new NotificationManager();
        SmtpMailer smtpMailer =  new SmtpMailer("smtp.google.com", order.getClientEmail());
        TelegramSender telegramSender = new TelegramSender("@user");
        notificationManager.registerNotificationService(smtpMailer);
        notificationManager.registerNotificationService(telegramSender);
        notificationManager.registerNotificationService(new NotificationLogger());

        var processor = new OrderProcessor(
                new DatabaseCache(new RandomSQLDatabase()),
                notificationManager
        );

        // 3. Обработка заказа
        try {
            processor.Process(order);
            processor.Process(order);
        } catch (Exception e) {
            System.out.println("Failed to process order: ");
            throw new RuntimeException(e);
        }

        // 4. Работа с обслуживанием
        System.out.println("\nTesting Warehouse Stuff:");
        List<Worker> workers = List.of(
                new HumanManager(),
                new RobotPacker("George Droid"));

        Warehouse.manageWarehouse(workers);
    }
}
