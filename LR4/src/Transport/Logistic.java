package Transport;

import Utilities.DataStore;

public abstract class Logistic {
    public Transport createTransport(String transportName) {
        double[] data = DataStore.getInstance().getTransportData(transportName);
        if (data == null) throw new IllegalArgumentException("Транспорт не найден: " + transportName);

        return buildSpecificTransport(transportName, data[0], data[1]);
    }

    protected abstract Transport buildSpecificTransport(String name, double expensePerKm, double speedKmH);
}
