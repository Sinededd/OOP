package Transport;

public abstract class Transport {
    private String name;
    private double expensePerKm;
    private double speedKmH;

    public Transport(String name, double expensePerKm, double speedKmH) {
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
}
