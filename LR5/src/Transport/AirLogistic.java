package Transport;

public class AirLogistic extends Logistic{
    @Override
    protected Transport buildSpecificTransport(String name, double expensePerKm, double speedKmH) {
        return new AirTransport(name, expensePerKm, speedKmH);
    }
}
