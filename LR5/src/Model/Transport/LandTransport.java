package Model.Transport;

public class LandTransport extends Transport {
    public LandTransport(String name, double expensePerKm, double speedKmH) {
        super(name, "Земля", expensePerKm, speedKmH);
    }
}
