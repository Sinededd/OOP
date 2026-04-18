package Utilities.LogisticStore;

import Model.Order.CargoSpecs;
import Model.Transport.TransportSpecs;
import Utilities.LogisticLoader.DataLoader;

import java.util.Map;

public class InMemoryDataStore implements DataStore{
    private Map<String, CargoSpecs> cargoCatalog;
    private Map<String, TransportSpecs> transportCatalog;

    public InMemoryDataStore(DataLoader loader)
    {
        load(loader);
    }

    public Map<String, CargoSpecs> getCargoCatalog() {
        return cargoCatalog;
    }

    public Map<String, TransportSpecs> getTransportCatalog() {
        return transportCatalog;
    }

    @Override
    public CargoSpecs getCargoSpecs(String name) {
        return cargoCatalog.get(name);
    }

    @Override
    public TransportSpecs getTransportSpecs(String name) {
        return transportCatalog.get(name);
    }

    @Override
    public void load(DataLoader loader) {
        cargoCatalog = loader.loadCargoSpecs();
        transportCatalog = loader.loadTransportSpecs();
    }
}