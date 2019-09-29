package application.model.squares;

import application.controller.GameController;
import application.model.Board;
import application.model.Game;
import application.model.Player;
import application.model.cards.Card;

public class CommunityChestSquare extends Square {
    public CommunityChestSquare(String name) {
        super(name);
    }

    @Override
    public void processPlayer(Player player, GameController gameController) {
        Card card = Board.getCommunityChestCard();
        card.process(player, gameController);
    }
}
