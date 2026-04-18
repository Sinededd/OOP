package Utilities.OrderSave;

import Model.Order.Order;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;

public class EncryptionDecorator extends OrderSaverDecorator {
    public EncryptionDecorator(OrderSaver saver) {
        super(saver);
    }

    @Override
    public void save(List<Order> orders, String filename) {
        super.save(orders, filename);
        try {
            byte[] content = Files.readAllBytes(Paths.get(filename));
            String encrypted = Base64.getEncoder().encodeToString(content);
            Files.write(Paths.get(filename), encrypted.getBytes());
            System.out.println("Данные в файле зашифрованы.");
        } catch (IOException e) {
            System.err.println("Ошибка шифрования: " + e.getMessage());
        }
    }
}