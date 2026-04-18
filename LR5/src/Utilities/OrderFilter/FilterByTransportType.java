package Utilities.OrderFilter;

import Model.Order.Order;

import java.util.List;
import java.util.stream.Collectors;

public class FilterByTransportType implements Filterable<Order> {
    private final String type;

    public FilterByTransportType(String type) {
        this.type = type;
    }

    @Override
    public List<Order> filter(List<Order> orders) {
        return orders.stream()
                .filter(o -> o.getTransport().getType().equalsIgnoreCase(type))
                .collect(Collectors.toList());
    }
}