package application.model.squares;

import application.controller.GameController;
import application.model.Player;

public class FreeParkingSquare extends Square {

    public FreeParkingSquare(String name) {
        super(name);
    }

    @Override
    public void processPlayer(Player player, GameController gameController) {

    }
}
