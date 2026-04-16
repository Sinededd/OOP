import Order.Order;
import Transport.AirLogistic;

public static void main(String[] args) {
    Order order = new Order(50.0, new AirLogistic(), "Грузовик (Земля)");

    order.addCargo("Электроника", 10);
    order.addCargo("Одежда", 5);

    System.out.println("=== " + "Заказ №1" + " ===");
    System.out.printf("Общая стоимость (P): %.2f\n", order.getTotalCost());
    System.out.printf("Время доставки: %.2f ч.\n", order.getTime());

    System.out.println("\nСостав партии:");
    for(var cargo : order.getBatch().getCargoList()) {
        System.out.printf(" - %s (х%d шт) | Общий вес: %.2f кг | Стоимость по весу: %.2f\n",
                cargo.getName(), cargo.getQuantity(), cargo.getTotalMass(), cargo.getCost());
    }
}
