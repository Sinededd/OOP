package Order;

public class Cargo {
    private final String name;
    private final int quantity;
    private final double unitMass;
    private final double costPerKg;

    public Cargo(String name, int quantity, double unitMass, double costPerKg) {
        this.name = name;
        this.quantity = quantity;
        this.unitMass = unitMass;
        this.costPerKg = costPerKg;
    }

    public double getTotalMass() {
        return quantity * unitMass;
    }

    public double getCost() {
        return getTotalMass() * costPerKg;
    }

    public String getName() { return name; }

    public int getQuantity() { return quantity; }
}
