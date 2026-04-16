package Utilities;

public interface DataStore {
    double[] getCargoData(String name);
    double[] getTransportData(String name);

    void load(DataLoader loader);
}
