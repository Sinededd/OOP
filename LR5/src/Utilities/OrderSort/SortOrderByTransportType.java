package Utilities.OrderSort;

import Model.Order.Order;

public class SortOrderByTransportType implements Sortable<Order> {
    @Override
    public void sort(java.util.List<Order> orders) {
        orders.sort(java.util.Comparator.comparing(o -> o.getTransport().getType()));
    }
}
