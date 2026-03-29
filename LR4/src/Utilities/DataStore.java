package Utilities;

import java.util.HashMap;
import java.util.Map;

public class DataStore {
    private static DataStore instance;

    private final Map<String, double[]> cargoCatalog = new HashMap<>();
    private final Map<String, double[]> transportCatalog = new HashMap<>();

    private DataStore() {
        loadData();
    }

    public static DataStore getInstance() {
        if (instance == null) {
            instance = new DataStore();
        }
        return instance;
    }

    private void loadData() {
        String data =
                "cargo;Электроника;1.5;50;;\n" +
                        "cargo;Одежда;0.8;20;;\n" +
                        "cargo;Оборудование;120.0;15;;\n" +
                        "cargo;Скоропортящиеся продукты;10.0;100;;\n" +
                        "transport;Грузовик (Земля);; ;15.0;80\n" +
                        "transport;Поезд (Земля);; ;5.0;60\n" +
                        "transport;Танкер (Вода);; ;2.0;35\n" +
                        "transport;Самолет (Воздух);; ;150.0;850\n" +
                        "transport;Вертолет (Воздух);; ;200.0;250";

        for (String line : data.split("\n")) {
            String[] parts = line.split(";");
            if (parts[0].equals("cargo")) {
                cargoCatalog.put(parts[1], new double[]{ Double.parseDouble(parts[2]), Double.parseDouble(parts[3]) });
            } else if (parts[0].equals("transport")) {
                transportCatalog.put(parts[1], new double[]{ Double.parseDouble(parts[4]), Double.parseDouble(parts[5]) });
            }
        }
    }

    public double[] getCargoData(String name) {
        return cargoCatalog.get(name);
    }

    public double[] getTransportData(String name) {
        return transportCatalog.get(name);
    }
}