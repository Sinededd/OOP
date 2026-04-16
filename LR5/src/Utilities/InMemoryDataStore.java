package Utilities;

import java.util.HashMap;
import java.util.Map;

public class InMemoryDataStore {

    private final Map<String, double[]> cargoCatalog = new HashMap<>();
    private final Map<String, double[]> transportCatalog = new HashMap<>();

    public double[] getCargoData(String name) {
        return cargoCatalog.get(name);
    }

    public double[] getTransportData(String name) {
        return transportCatalog.get(name);
    }
}