package Utilities.OrderSort;

import java.util.Collections;
import java.util.List;

public class ReverseSorter<T> implements Sortable<T> {
    private final Sortable<T> originalSorter;

    public ReverseSorter(Sortable<T> originalSorter) {
        this.originalSorter = originalSorter;
    }

    @Override
    public void sort(List<T> items) {
        originalSorter.sort(items);
        Collections.reverse(items);
    }
}