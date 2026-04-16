package Transport;

public class LandLogistic extends Logistic{
    @Override
    protected Transport buildSpecificTransport(String name, double expensePerKm, double speedKmH) {
        IO.println("Наземный транспорт создан");
        return new LandTransport(name, expensePerKm, speedKmH);
    }
}
