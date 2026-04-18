package Utilities.OrderFilter;

import Model.Order.Order;

import java.util.List;
import java.util.stream.Collectors;

public class FilterByTime implements Filterable<Order> {
    private final double maxTime;

    public FilterByTime(double maxTime) {
        this.maxTime = maxTime;
    }

    @Override
    public List<Order> filter(List<Order> orders) {
        return orders.stream()
                .filter(o -> o.getTime() <= maxTime)
                .collect(Collectors.toList());
    }
}