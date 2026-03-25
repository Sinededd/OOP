package order;

import models.Item;
import models.Order;

import java.util.stream.Stream;

public class OrderAmount {
    public static double calculatingAmount(Order order)
    {
        double price = order.getType().OrderConditions(order, order.getItems().stream().mapToDouble(Item::getPrice).sum());

        //Рассчет стоимости с скидочной картой
        if(order.getDiscountCard() != null)
            price = order.getDiscountCard().apply(price);

        return price;
    }
}
