package order;

import models.Order;

public interface OrderType {
    String getTypeName();
    double OrderConditions(Order order, double total);
    default void validate(Order order) {}
}
