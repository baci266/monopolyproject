package application.model.squares;

import application.controller.GameController;
import application.model.Player;

public class GoSquare extends Square {

    public int startMoney;

    public GoSquare(String name, int startMoney) {
        super(name);
        this.startMoney = startMoney;
    }

    public int getStartMoney() {
        return startMoney;
    }

    @Override
    public void processPlayer(Player player, GameController gameController) {
        player.addMoney(startMoney*2);
    }
}
