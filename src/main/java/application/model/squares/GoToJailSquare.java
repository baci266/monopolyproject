package application.model.squares;

import application.controller.GameController;
import application.model.Board;
import application.model.Player;

public class GoToJailSquare extends Square {

    public GoToJailSquare(String name) {
        super(name);
    }

    @Override
    public void processPlayer(Player player, GameController gameController) {
        player.setPosition(Board.getJailPosition());
        gameController.movePlayerToJail(player);
    }
}
