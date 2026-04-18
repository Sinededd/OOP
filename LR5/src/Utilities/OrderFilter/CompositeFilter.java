package Utilities.OrderFilter;

import java.util.ArrayList;
import java.util.List;

public class CompositeFilter<T> implements Filterable<T> {
    private final List<Filterable<T>> filters = new ArrayList<>();

    public void addFilter(Filterable<T> filter) {
        filters.add(filter);
    }

    @Override
    public List<T> filter(List<T> items) {
        List<T> result = items;
        for (Filterable<T> filter : filters) {
            result = filter.filter(result);
        }
        return result;
    }
}