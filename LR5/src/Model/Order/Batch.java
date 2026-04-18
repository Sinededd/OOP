package Model.Order;

import Utilities.LogisticStore.DataStore;

import java.util.ArrayList;
import java.util.List;

public class Batch {
    private final List<Cargo> cargoList = new ArrayList<>();
    private final DataStore dataStore;

    public Batch(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    public void addCargo(String name, int quantity) {
        CargoSpecs data = dataStore.getCargoSpecs(name);
        if (data != null) {
            cargoList.add(new Cargo(quantity, data));
        } else {
            throw new IllegalArgumentException("Товар не найден в базе: " + name);
        }
    }

    public double getCost() {
        return cargoList.stream().mapToDouble(Cargo::getCost).sum();
    }

    public List<Cargo> getCargoList() {
        return cargoList;
    }
}
