package order;

import models.Order;

public class OrderValidator {

    public static void validateOrder(Order order)
    {
        if (order.getItems().isEmpty()) {
            throw new RuntimeException("order must have at least one item");
        }
        if (order.getDestination().getCity().isEmpty()) {
            throw new RuntimeException("destination city is required");
        }

        order.getType().validate(order);
    }
}
