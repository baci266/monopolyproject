package application.model.squares;

import application.controller.GameController;
import application.model.Player;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;

public abstract class Square extends Button {
    public String name;

    public Square(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public abstract void processPlayer(Player player, GameController gameController);

    public double getCenterX(){
        Bounds bounds = getBoundsInParent();
        return  (bounds.getMinX() + (bounds.getMaxX()-bounds.getMinX())/2);
    }
    public double getCenterY(){
        Bounds bounds = getBoundsInParent();
        return (bounds.getMinY() + (bounds.getMaxY()-bounds.getMinY())/2)-25;
    }
}
