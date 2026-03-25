package order;

import models.Order;

public class OrderStandardType implements OrderType{

    @Override
    public String getTypeName() {
        return "Standard";
    }

    @Override
    public double OrderConditions(Order order, double total) {
        return total * 1.2;
    }
}
