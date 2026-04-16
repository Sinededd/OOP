package Transport;

import Utilities.InMemoryDataStore;

public abstract class Logistic {
    public Transport createTransport(String transportName) {
        double[] data = InMemoryDataStore.getInstance().getTransportData(transportName);
        if (data == null) throw new IllegalArgumentException("Транспорт не найден: " + transportName);

        return buildSpecificTransport(transportName, data[0], data[1]);
    }

    protected abstract Transport buildSpecificTransport(String name, double expensePerKm, double speedKmH);
}
