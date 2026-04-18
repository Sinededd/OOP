package Utilities.OrderSave;

import Model.Order.Order;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvOrderSaver implements OrderSaver {
    @Override
    public void save(List<Order> orders, String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("Transport,Type,Distance,Time,TotalCost\n");
            for (Order o : orders) {
                writer.write(String.format("%s,%s,%.2f,%.2f,%.2f\n",
                        o.getTransport().getName(), o.getTransport().getType(),
                        o.getDistance(), o.getTime(), o.getTotalCost()));
            }
            System.out.println("Данные сохранены в CSV: " + filename);
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении CSV: " + e.getMessage());
        }
    }
}
