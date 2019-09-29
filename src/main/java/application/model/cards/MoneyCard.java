package application.model.cards;

import application.controller.GameController;
import application.model.Player;

public class MoneyCard extends Card{
    int value;

    public MoneyCard(String desription, int value) {
        super(desription);
        this.value = value;
    }

    @Override
    public void process(Player player, GameController gameController) {
        if(value < 0){
            player.payMoney(value);
        }
        else {
            player.addMoney(value);
        }
        gameController.showCardDialog(this.description);
    }
}
