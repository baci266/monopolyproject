package application.model;

import application.Logger;
import application.View;
import application.model.squares.OwnableSquare;
import application.model.squares.Square;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public class Player extends ImageView {
    private int id_player;
    public String name;
    private int position = 0;
    public List<OwnableSquare> properties = new ArrayList<>();
    private int money;
    private String icon;
    private int jailTime = 0;

    public Player(String name, String icon) {
        this.name = name;
        this.icon = icon;
        this.money = GameOptions.START_MONEY;
        setImage(new Image(View.PATH_TO_IMAGES + icon + ".png"));
    }

    /**
     *
     * @param id_player
     * @param name
     * @param position
     * @param money
     * @param icon
     * @param jailTime
     */
    public Player(int id_player, String name, int position, int money, String icon, int jailTime) {
        this.id_player = id_player;
        this.name = name;
        this.position = position;
        this.money = money;
        this.icon = icon;
        this.jailTime = jailTime;
    }

    public void addProperties(List<OwnableSquare> properties){
        this.properties = properties;
    }
    /**
     * Move player - if player goes over start then player get start money
     * @param distance distance
     */
    public void makeMove(int distance){
        int oldPosition = position;
        position += distance;

        if(position>=GameOptions.SQUARES_COUNT){
            position %= GameOptions.SQUARES_COUNT;
            addMoney(GameOptions.MONEY_FROM_START);
        }

        Logger.logPlayerActivity(this, "Move from position " + oldPosition + " to " + position);
    }

    /**
     * Check if player is without money
     * @return boolean
     */
    public boolean isBroke(){
        return money <= 0;
    }

    public boolean isInJail() {
        return jailTime-- > 0;
    }

    public void sendToJail(){
        jailTime = 2;
    }

    public String getName() {
        return name;
    }

    public List<OwnableSquare> getPropertySquares() {
        return properties;
    }

    private void addProperty(OwnableSquare property) {
        properties.add(property);
    }

    public boolean ownProperty(OwnableSquare square){
        return properties.contains(square);
    }

    public int getMoney() {
        return money;
    }

    public void addMoney(int amount){
        money += amount;
    }

    public void payToPlayer(Player player, int amount){
        player.addMoney(amount);
        payMoney(amount);
        Logger.log("Player: " + name + " pays " + amount + " to " + player.getName());
    }

    public void payMoney(int amount){
        money -= amount;
        Logger.logPlayerActivity(this,"pays " + amount);
    }

    public void buyProperty(Square square){
        OwnableSquare property = null;
        if(square instanceof OwnableSquare){
            property = (OwnableSquare) square;
        }
        if (property != null && canBuyItem(property)) {
            addProperty(property);
            payMoney(property.getPrice());
            property.setOwner(this);
            Logger.log("Player: " + name + " buys " + property.getName());
        } else {
            Logger.log("Player: " + name + " can't buy this item");
        }
    }

    public void setPosition(int position){
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public boolean canBuyItem(OwnableSquare square) {
        return square.owner == null && square.getPrice() <= money;
    }

    public boolean canUpgrade(OwnableSquare ownableSquare) {
        return ownProperty(ownableSquare) && ownableSquare.canBeUpgrade();
    }

    public int getJailTime() {
        return jailTime;
    }

    public String getIcon() {
        return icon;
    }

    public int getIdPlayer() {
        return id_player;
    }

    public void setIdPlayer(int playerId) {
        this.id_player = playerId;
    }
}
