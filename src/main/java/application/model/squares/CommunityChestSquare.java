package application.model.squares;

import application.controller.GameController;
import application.model.Player;

public class CommunityChestSquare extends Square {
    public CommunityChestSquare(String name) {
        super(name);
    }

    @Override
    public void processPlayer(Player player, GameController gameController) {
        //TODO: impelemntovat podla pravidiel zatial zaplati 100
        player.payMoney(100);
    }
}
