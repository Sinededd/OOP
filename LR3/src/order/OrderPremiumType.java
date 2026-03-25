package order;

import models.Order;

public class OrderPremiumType implements OrderType{

    @Override
    public String getTypeName() {
        return "Premium";
    }

    @Override
    public double OrderConditions(Order order, double total) {
        return total * 0.9 * 1.2;
    }
}
