package Utilities.OrderSave;

import Model.Order.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class JsonOrderSaver implements OrderSaver {

    private final ObjectMapper objectMapper;

    public JsonOrderSaver() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @Override
    public void save(List<Order> orders, String filename) {
        try {
            objectMapper.writeValue(new File(filename), orders);
            System.out.println("Данные успешно экспортированы в JSON: " + filename);
        } catch (IOException e) {
            System.err.println("Ошибка при записи JSON: " + e.getMessage());
        }
    }
}