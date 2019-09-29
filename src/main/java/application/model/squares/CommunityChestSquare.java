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
        Game game = gameController.mainController.game;
        Board board = game.getBoard();
        Card card = board.getCommunityChestCard();
        card.process(player, gameController);
    }
}
