package staff;

/**
 * RobotPacker - Робот
 */
public class RobotPacker implements Worker, OrderTask, BreakTaker {
    private String model;

    public RobotPacker(String model) {
        this.model = model;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public void processOrder() {
        System.out.println("Robot " + model + " is packing boxes...");
    }

    @Override
    public void getRest() {
        System.out.println("Robot was taken for maintenance");
    }

    @Override
    public void performRoutine() {
        processOrder();
        getRest();
    }
}
