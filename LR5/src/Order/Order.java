package Order;

import Transport.Transport;
import Transport.Logistic;

public class Order {
    private Batch batch;
    private double distance;
    private Transport transport;

    public Order(double distance, Logistic logistic, String transportName)
    {
        this.batch = new Batch();
        this.distance = distance;
        transport = logistic.createTransport(transportName);
    }

    public void addCargo(String name, int quantity) {
        batch.addCargo(name, quantity);
    }

    public double getTotalCost() {
        return batch.getCost() + transport.getExpense(distance);
    }

    public double getTime()
    {
        return transport.getTime(distance);
    }

    public double getExpense()
    {
        return transport.getExpense(distance);
    }

    public Batch getBatch() {
        return batch;
    }

    public void setBatch(Batch batch) {
        this.batch = batch;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Transport getTransport() {
        return transport;
    }

    public void setTransport(Transport transport) {
        this.transport = transport;
    }
}
