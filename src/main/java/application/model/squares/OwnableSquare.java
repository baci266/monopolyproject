package application.model.squares;

import application.controller.GameController;
import application.model.Player;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.input.MouseEvent;

public abstract class OwnableSquare extends Square {

    private int rent;
    private int price;
    private Player owner;

    public OwnableSquare(String name, int rent, int price) {
        super(name);
        this.rent = rent;
        this.price = price;
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> showPropertyInfo());
    }

    private void showPropertyInfo() {
        Dialog dialog = new Dialog<>();
        dialog.setTitle("Property info");
        dialog.setHeaderText("Property owner: " + getOwner().getName() + "\n" +
                              "Property level:" + getLevel());

        ButtonType buttonTypeOk = new ButtonType("Got it", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
        dialog.show();
    }

    public int getRent() {
        return rent;
    }

    public void setRent(int rent) {
        this.rent = rent;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {

        this.owner = owner;
    }

    public boolean isOwned() {
        return owner != null;
    }

    @Override
    public void processPlayer(Player player, GameController gameController) {
        if(owner!=null && owner != player){
            player.payToPlayer(owner, rent);
            return;
        }
        if(player.canBuyItem(this)){
            gameController.enableButtons(gameController.buyButton);
        }
        if(player.canUpgrade(this)){
            gameController.enableButtons(gameController.upgradeButton);
        }
    }

    public abstract boolean canBeUpgrade();

    public String getLevel(){
        return "DEFAULT";
    }

    public void setLevel(String property_level) {
        //ale nikto nic
    }
}
