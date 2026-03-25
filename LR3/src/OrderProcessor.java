import infrastructure.IDatabase;
import infrastructure.INotification;
import infrastructure.NotificationManager;
import models.Order;
import order.OrderAmount;
import order.OrderValidator;

/**
 * Основная бизнес-логика.
 */
public class OrderProcessor {
    private final IDatabase database;
    private final NotificationManager notificationManager;

    public OrderProcessor(IDatabase database, NotificationManager notificationManager) {
        this.database = database;
        this.notificationManager = notificationManager;
    }

    public void Process(Order order) {
        System.out.printf("--- Processing Order %s ---\n", order.getId());
        var orderItems = order.getItems();

        // 1. Логика валидации
        ValidateOrder(order);

        // 2. Логика расчета суммы с учетом тарифа
        double total = OrderAmount.calculatingAmount(order);

        // 4. Логика сохранения
        SaveOrder(order, total);

        // 5. Логика уведомлений
        sendNotification(order, total);
    }

    public void ValidateOrder(Order order)
    {
        OrderValidator.validateOrder(order);
    }

    public void SaveOrder(Order order, double total)
    {
        try {
            database.saveOrder(order, total);
        } catch (Exception e) {
            System.err.println("database error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void sendNotification(Order order, double total)
    {
        var emailBody = String.format("<h1>Your order %s is confirmed!</h1><p>Total: %.2f</p>", order.getId(), total);
        notificationManager.sendNotification("Order Confirmation", emailBody);
    }
}
