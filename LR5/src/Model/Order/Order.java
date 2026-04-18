package Model.Order;

import Model.Transport.Transport;

public class Order {
    private final Batch batch;
    private final double distance;
    private final Transport transport;

    public Order(double distance, Batch batch, Transport transport) {
        this.batch = batch;
        this.distance = distance;
        this.transport = transport;
    }

    public double getTotalCost() {
        return batch.getCost() + transport.getExpense(distance);
    }

    public double getTime() {
        return transport.getTime(distance);
    }

    public Batch getBatch() { return batch; }
    public double getDistance() { return distance; }
    public Transport getTransport() { return transport; }

}