package Utilities.OrderFilter;

import Model.Order.Order;

import java.util.List;
import java.util.stream.Collectors;

public class FilterByCost implements Filterable<Order> {
    private final double maxCost;

    public FilterByCost(double maxCost) {
        this.maxCost = maxCost;
    }

    @Override
    public List<Order> filter(List<Order> orders) {
        return orders.stream()
                .filter(o -> o.getTotalCost() <= maxCost)
                .collect(Collectors.toList());
    }
}