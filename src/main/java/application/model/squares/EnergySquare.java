package application.model.squares;

public class EnergySquare extends OwnableSquare {
    //TODO: implementovat rent bude 4 krat hod kockou
    public EnergySquare(String name, int price, int rent) {
        super(name, rent, price);
    }

    @Override
    public boolean canBeUpgrade() {
        return false;
    }
}
