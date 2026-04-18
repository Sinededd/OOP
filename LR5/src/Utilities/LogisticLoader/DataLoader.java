package Utilities.LogisticLoader;

import Model.Order.CargoSpecs;
import Model.Transport.TransportSpecs;

import java.util.Map;

public interface DataLoader {
    Map<String, CargoSpecs> loadCargoSpecs();
    Map<String, TransportSpecs> loadTransportSpecs();
}
