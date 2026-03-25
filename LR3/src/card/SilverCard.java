package card;

public class SilverCard implements IDiscountCard{
    @Override
    public double apply(double price) {
        return price * 0.9;
    }

    @Override
    public String getCardName() {
        return "Silver";
    }
}
