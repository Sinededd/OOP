package card;

public class NewbieCard implements IDiscountCard{

    @Override
    public double apply(double price) {
        return price;
    }

    @Override
    public String getCardName() {
        return "Newbie";
    }
}
