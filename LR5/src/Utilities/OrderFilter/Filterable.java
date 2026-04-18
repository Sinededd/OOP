package Utilities.OrderFilter;

import java.util.List;

public interface Filterable<T> {
    List<T> filter(List<T> items);
}