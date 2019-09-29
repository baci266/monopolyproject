package application.model.squares;

import application.View;
import application.controller.GameController;
import application.model.Player;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;

public class EnergySquare extends OwnableSquare {
    public EnergySquare(String name, int price, int rent) {
        super(name, rent, price);
    }

    @Override
    public boolean canBeUpgrade() {
        return false;
    }

    @Override
    public void processPlayer(Player player, GameController gameController) {
        if(getOwner() != null && getOwner() != player){
            int i = 4;

            int rent = 0;
            while (i > 0) {
                Dialog dialog = new Dialog<>();
                dialog.setTitle("Energy square");
                dialog.setHeaderText("Roll dice "+ i + " times. Rent to pay: " + rent);

                ButtonType buttonTypeOk = new ButtonType("Roll dice", ButtonBar.ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
                dialog.showAndWait();
                int dice1 = gameController.mainController.game.getDice().rollFirstDice();
                gameController.dice1.setImage(new Image(View.PATH_TO_IMAGES + "dice" + dice1 + ".png"));
                gameController.dice2.setImage(null);
                rent += dice1;
                i--;
            }
            player.payToPlayer(getOwner(), rent);
            return;
        }
        if(player.canBuyItem(this)){
            gameController.enableButtons(gameController.buyButton);
        }
        if(player.canUpgrade(this)){
            gameController.enableButtons(gameController.upgradeButton);
        }
    }
}
