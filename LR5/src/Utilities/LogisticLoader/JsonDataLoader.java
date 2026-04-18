package Utilities.LogisticLoader;

import Model.Order.CargoSpecs;
import Model.Transport.TransportSpecs;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JsonDataLoader implements DataLoader {
    private String path;
    private final ObjectMapper mapper;

    public JsonDataLoader(String path) {
        this.path = path;
        
        this.mapper = new ObjectMapper();
    }

    @Override
    public Map<String, CargoSpecs> loadCargoSpecs() {
        Map<String, CargoSpecs> cargoData = new HashMap<>();

        try {
            
            JsonNode root = mapper.readTree(new File(path));
            JsonNode cargosNode = root.path("cargos");

            if (cargosNode.isArray()) {
                for (JsonNode node : cargosNode) {
                    String name = node.path("name").asText();
                    double mass = node.path("mass").asDouble();
                    double costPerKg = node.path("costPerKg").asDouble();

                    if (!name.trim().isEmpty()) {
                        cargoData.put(name, new CargoSpecs(name, mass, costPerKg));
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка чтения JSON файла (cargo): " + e.getMessage());
        }

        return cargoData;
    }

    @Override
    public Map<String, TransportSpecs> loadTransportSpecs() {
        Map<String, TransportSpecs> transportData = new HashMap<>();

        try {
            JsonNode root = mapper.readTree(new File(path));
            JsonNode transportsNode = root.path("transports");

            if (transportsNode.isArray()) {
                for (JsonNode node : transportsNode) {
                    String name = node.path("name").asText();
                    
                    String type = node.path("type").asText("Неизвестно");
                    double consumption = node.path("consumption").asDouble();
                    double speed = node.path("speed").asDouble();

                    if (!name.trim().isEmpty()) {
                        transportData.put(name, new TransportSpecs(name, type, consumption, speed));
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка чтения JSON файла (transport): " + e.getMessage());
        }

        return transportData;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}