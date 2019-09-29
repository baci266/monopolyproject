package application.model.squares;

import application.controller.GameController;
import application.model.Player;

public class TaxSquare extends Square{

    public int taxValue;

    public TaxSquare(String name, int taxValue) {
        super(name);
        this.taxValue = taxValue;
    }

    @Override
    public void processPlayer(Player player, GameController gameController) {
        player.payMoney(taxValue);
    }
}
