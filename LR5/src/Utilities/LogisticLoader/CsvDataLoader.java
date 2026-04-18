package Utilities.LogisticLoader;

import Model.Order.CargoSpecs;
import Model.Transport.TransportSpecs;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CsvDataLoader implements DataLoader {
    private String path;

    public CsvDataLoader(String path) {
        this.path = path;
    }

    @Override
    public Map<String, CargoSpecs> loadCargoSpecs() {
        Map<String, CargoSpecs> cargoData = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            br.readLine();

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(";");

                if (parts.length < 4) continue;

                String type = parts[0].trim();
                if (!"cargo".equals(type)) continue;

                String name = parts[1].trim();
                if (name.isEmpty()) continue;

                try {
                    double mass = Double.parseDouble(parts[2].trim());
                    double costPerKg = Double.parseDouble(parts[3].trim());

                    cargoData.put(name, new CargoSpecs(name, mass, costPerKg));
                } catch (NumberFormatException e) {
                    System.err.println("Ошибка парсинга cargo: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + e.getMessage());
            e.printStackTrace();
        }

        return cargoData;
    }

    @Override
    public Map<String, TransportSpecs> loadTransportSpecs() {
        Map<String, TransportSpecs> transportData = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            br.readLine();

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(";");
                if (parts.length < 6) continue;

                String recordType = parts[0].trim();
                if (!"transport".equals(recordType)) continue;

                String rawName = parts[1].trim();
                if (rawName.isEmpty()) continue;

                String cleanName = rawName;
                String transportType = "Неизвестно";

                if (rawName.contains("(") && rawName.contains(")")) {
                    int openBracket = rawName.indexOf("(");
                    int closeBracket = rawName.indexOf(")");

                    cleanName = rawName.substring(0, openBracket).trim();
                    transportType = rawName.substring(openBracket + 1, closeBracket).trim();
                }

                try {
                    double consumption = Double.parseDouble(parts[4].trim());
                    double speed = Double.parseDouble(parts[5].trim());

                    transportData.put(cleanName, new TransportSpecs(cleanName, transportType, consumption, speed));
                } catch (NumberFormatException e) {
                    System.err.println("Ошибка парсинга чисел в transport: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + e.getMessage());
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