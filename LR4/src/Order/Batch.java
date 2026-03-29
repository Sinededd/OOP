package Order;

import Utilities.DataStore;

import java.util.ArrayList;
import java.util.List;

public class Batch {
    private final List<Cargo> cargoList = new ArrayList<>();

    public void addCargo(String name, int quantity) {
        double[] data = DataStore.getInstance().getCargoData(name);
        if (data != null) {
            cargoList.add(new Cargo(name, quantity, data[0], data[1]));
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
