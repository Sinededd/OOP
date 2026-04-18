package Model.Transport;

public class AirTransport extends Transport{
    public AirTransport(String name, double expensePerKm, double speedKmH) {
        super(name, "Воздух",expensePerKm, speedKmH);
    }
}
