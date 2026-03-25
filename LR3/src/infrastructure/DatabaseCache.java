package infrastructure;

import models.Order;

import java.util.HashSet;
import java.util.Set;

public class DatabaseCache implements IDatabase {
    private final IDatabase database;
    private final Set<String> cache = new HashSet<>();

    public DatabaseCache(IDatabase database)
    {
        this.database = database;
    }

    @Override
    public void saveOrder(Order order, double total) throws InterruptedException {
        if(cache.contains(order.getId())) {
            System.out.println(">> [Cache] Заказ " + order.getId() + " уже есть в кэше.");
            return;
        }

        database.saveOrder(order, total);
        cache.add(order.getId());
    }

    public IDatabase getDatabase() {
        return database;
    }

    public  void clearCache()
    {
        cache.clear();
    }
}
