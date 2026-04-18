package Utilities.OrderSave;

import Model.Order.Order;

import java.util.List;

public interface OrderSaver {
    void save(List<Order> orders, String filename);
}
