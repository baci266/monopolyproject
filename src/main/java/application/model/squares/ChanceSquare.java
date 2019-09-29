package application.model.squares;

import application.controller.GameController;
import application.model.Player;

public class ChanceSquare extends Square {
    public ChanceSquare(String name) {
        super(name);
    }

    @Override
    public void processPlayer(Player player, GameController gameController) {
        //TODO: implementovat; zatial dostane 100
        player.addMoney(100);
    }

}
