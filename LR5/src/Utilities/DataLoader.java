package Utilities;

import java.util.Map;

public interface DataLoader {
    Map<String, double[]> loadCargoData();
    Map<String, double[]> loadTransportData();
}
