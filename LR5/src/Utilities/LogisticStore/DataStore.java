package Utilities.LogisticStore;

import Model.Order.CargoSpecs;
import Model.Transport.TransportSpecs;
import Utilities.LogisticLoader.DataLoader;

public interface DataStore {
    CargoSpecs getCargoSpecs(String name);
    TransportSpecs getTransportSpecs(String name);

    void load(DataLoader loader);
}
