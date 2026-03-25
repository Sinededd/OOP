package order;

import models.Order;

public class OrderBudgetType implements OrderType{

    @Override
    public String getTypeName() {
        return "Budget";
    }

    @Override
    public double OrderConditions(Order order, double total) {
        return total;
    }

    @Override
    public void validate(Order order) {
        if (order.getItems().size() > 3) {
            throw new RuntimeException("Budget orders cannot have more than 3 items.");
        }
    }
}
