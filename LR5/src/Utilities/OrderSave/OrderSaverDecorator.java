package Utilities.OrderSave;

import Model.Order.Order;

import java.util.List;

abstract class OrderSaverDecorator implements OrderSaver {
    protected OrderSaver decoratedSaver;

    public OrderSaverDecorator(OrderSaver saver) {
        this.decoratedSaver = saver;
    }

    public void save(List<Order> orders, String filename) {
        decoratedSaver.save(orders, filename);
    }
}