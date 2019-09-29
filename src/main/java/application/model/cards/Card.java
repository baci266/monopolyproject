package application.model.cards;

import application.controller.GameController;
import application.model.Player;

public abstract class Card {
    protected String description;

    public Card(String description) {
        this.description = description;
    }
    public abstract void process(Player player, GameController gameController);

}
