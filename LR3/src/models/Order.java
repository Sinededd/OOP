package models;

import java.util.List;

import card.IDiscountCard;
import order.OrderType;

/**
 * Order - заказ
 */
public class Order {
    private String id;
    private List<Item> items;
    private OrderType type;
    private IDiscountCard discountCard;
    private String clientEmail;
    private Address destination;

    public Order(String id, OrderType type, List<Item> items, String clientEmail, Address destination) {
        this.id = id;
        this.items = items;
        this.type = type;
        this.discountCard = null;
        this.clientEmail = clientEmail;
        this.destination = destination;
    }

    public Order(String id, OrderType type, IDiscountCard discountCard, List<Item> items, String clientEmail, Address destination) {
        this.id = id;
        this.items = items;
        this.type = type;
        this.discountCard = discountCard;
        this.clientEmail = clientEmail;
        this.destination = destination;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public OrderType getType() {
        return type;
    }

    public void setType(OrderType type) {
        this.type = type;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public Address getDestination() {
        return destination;
    }

    public void setDestination(Address destination) {
        this.destination = destination;
    }

    public IDiscountCard getDiscountCard() {
        return discountCard;
    }

    public void setDiscountCard(IDiscountCard discountCard) {
        this.discountCard = discountCard;
    }
}
