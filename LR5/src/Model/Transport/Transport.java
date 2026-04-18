package Model.Transport;

public abstract class Transport {
    private String name;
    private String type;
    private double expensePerKm;
    private double speedKmH;

    public Transport(String name, String type, double expensePerKm, double speedKmH) {
        this.name = name;
        this.type = type;
        this.expensePerKm = expensePerKm;
        this.speedKmH = speedKmH;
    }

    public double getTime(double distance) {
        return distance / speedKmH;
    }

    public double getExpense(double distance) {
        return expensePerKm * distance;
    }

    public double getExpensePerKm() {
        return expensePerKm;
    }

    public void setExpensePerKm(double expensePerKm) {
        this.expensePerKm = expensePerKm;
    }

    public double getSpeedKmH() {
        return speedKmH;
    }

    public void setSpeedKmH(double speedKmH) {
        this.speedKmH = speedKmH;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
