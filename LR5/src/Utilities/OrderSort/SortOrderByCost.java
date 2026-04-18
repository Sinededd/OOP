package Utilities.OrderSort;

import Model.Order.Order;

import java.util.Comparator;
import java.util.List;

public class SortOrderByCost implements Sortable<Order> {
    @Override
    public void sort(List<Order> orders) {
        orders.sort(Comparator.comparingDouble(Order::getTotalCost));
    }
}
