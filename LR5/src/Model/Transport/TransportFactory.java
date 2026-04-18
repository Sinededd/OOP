package Model.Transport;

import Utilities.LogisticStore.DataStore;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class TransportFactory {
    DataStore dataStore;
    private final Map<String, BiFunction<String, TransportSpecs, Transport>> creators = new HashMap<>();

    public TransportFactory(DataStore dataStore) {
        this.dataStore = dataStore;

        creators.put("Земля", (name, specs) -> new LandTransport(name, specs.consumption(), specs.speed()));
        creators.put("Воздух", (name, specs) -> new AirTransport(name, specs.consumption(), specs.speed()));
        creators.put("Вода", (name, specs) -> new WaterTransport(name, specs.consumption(), specs.speed()));
    }

    public Transport createTransport(String transportName) {
        TransportSpecs specs = dataStore.getTransportSpecs(transportName);

        if (specs == null) {
            throw new IllegalArgumentException("Транспорт не найден: " + transportName);
        }

        BiFunction<String, TransportSpecs, Transport> creator = creators.get(specs.type());

        if (creator == null) {
            throw new IllegalStateException("Тип среды '" + specs.type() + "' не поддерживается");
        }

        return creator.apply(transportName, specs);
    }
}
