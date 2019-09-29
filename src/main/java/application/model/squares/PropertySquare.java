package application.model.squares;

import application.model.Player;
import application.model.squares.OwnableSquare;

public class PropertySquare extends OwnableSquare {
    private final int mortgageValue;
    private final int housePrice;

    private final int oneHouseRent;
    private final int twoHouseRent;
    private final int threeHouseRent;
    private final int fourHouseRent;
    private final int hotelRent;

    private PropertyState state = PropertyState.NO_HOUSE;

    private boolean isHotel;

    private boolean inColourMonopoly;

    public PropertySquare(String name, int price, int rent,
                          int oneHouseRent,int twoHouseRent,
                          int threeHouseRent, int fourHouseRent,
                          int hotelRent, int housePrice
                          ) {
        super(name,rent,price);

        this.oneHouseRent = oneHouseRent;
        this.twoHouseRent = twoHouseRent;
        this.threeHouseRent = threeHouseRent;
        this.fourHouseRent = fourHouseRent;
        this.hotelRent = hotelRent;
        this.housePrice = housePrice;

        this.mortgageValue = price / 2;
    }

    public void upgrade(){
        state = state.next();
    }
    public int priceForUpdate(){
        if (state != PropertyState.FOUR_HOUSES) {
            return price;
        }
        else {
            return price * 2;
        }

    }
    @Override
    public boolean canBeUpgrade() {
        return state != PropertyState.HOTEL;
    }

    @Override
    public String getLevel() {
        return state.toString();
    }

    @Override
    public void setLevel(String property_level) {
        state = PropertyState.valueOf(property_level);
    }
}
enum PropertyState {
    NO_HOUSE,
    ONE_HOUSE,
    TWO_HOUSES,
    THREE_HOUSES,
    FOUR_HOUSES,
    HOTEL;
    public PropertyState next()
    {
        switch (this){
            case NO_HOUSE: return ONE_HOUSE;
            case ONE_HOUSE: return TWO_HOUSES;
            case TWO_HOUSES: return THREE_HOUSES;
            case THREE_HOUSES: return FOUR_HOUSES;
            case FOUR_HOUSES: return HOTEL;
            default: return NO_HOUSE;
        }
    }
}
