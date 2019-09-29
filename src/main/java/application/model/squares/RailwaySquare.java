package application.model.squares;

import application.model.Player;
import application.model.squares.OwnableSquare;

public class RailwaySquare extends OwnableSquare {

    public RailwaySquare(String name, int price, int rent) {
        super(name, rent, price);
    }

    @Override
    public boolean canBeUpgrade() {
        return false;
    }

}
