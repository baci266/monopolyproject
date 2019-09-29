package application.model.squares;

import application.controller.GameController;
import application.model.Player;

public class JailSquare extends Square {

    public JailSquare(String name) {
        super(name);
    }

    @Override
    public void processPlayer(Player player, GameController gameController) {
        player.sendToJail();
    }
}
