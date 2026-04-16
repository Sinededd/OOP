package Transport;

public class WaterLogistic extends Logistic{
    @Override
    protected Transport buildSpecificTransport(String name, double expensePerKm, double speedKmH) {
        return new WaterTransport(name, expensePerKm, speedKmH);
    }
}
