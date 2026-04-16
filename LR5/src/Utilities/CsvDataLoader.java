package Utilities;

import java.util.Map;

public class CsvDataLoader implements DataLoader{
    @Override
    public Map<String, double[]> loadCargoData() {
        return Map.of();
    }

    @Override
    public Map<String, double[]> loadTransportData() {
        return Map.of();
    }
}
