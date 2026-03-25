package card;

public class GoldCard implements IDiscountCard{
    @Override
    public double apply(double price) {
        return price * 0.85;
    }

    @Override
    public String getCardName() {
        return "Gold";
    }
}
