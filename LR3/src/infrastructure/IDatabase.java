package infrastructure;

import models.Order;

public interface IDatabase {
    public void saveOrder(Order order, double total)  throws InterruptedException;
}
