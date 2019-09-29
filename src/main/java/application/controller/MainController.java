package application.controller;

import application.Database;
import application.Logger;
import application.View;
import application.model.Game;
import application.model.Player;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;

public class MainController {

    private Stage mainStage;
    private FXMLLoader loader;
    public Game game;

    public MainController(Stage primaryStage) {
        mainStage = primaryStage;
        mainStage.setResizable(false);
        displayMenu();
    }

    private void displayMenu(){
        mainStage.setTitle("Menu Monopoly");
        Scene menuScene = loadScene(View.MENU_VIEW);
        mainStage.setScene(menuScene);
        mainStage.show();
        MenuController menuController = loader.getController();
        menuController.setMainController(this);
    }

    public void startGame(String title, List<Player> players) {

        game = new Game(players,title);

        mainStage.setTitle(title);
        Scene gameScene = loadScene(View.GAME_VIEW);
        mainStage.setScene(gameScene);
        GameController gameController = loader.getController();
        gameController.setMainController(this);
        gameController.processBeforeMove();

    }

    public void loadGame(int game_id) {
        game = Database.loadGame(game_id);
        if (game == null || game.getPlayers() == null){
            showAlert("Error", "Failed to load game", "restart game");
            return;
        }
        mainStage.setTitle(game.getGameName());
        Scene gameScene = loadScene(View.GAME_VIEW);
        mainStage.setScene(gameScene);
        GameController gameController = loader.getController();
        gameController.setMainController(this);
        gameController.processBeforeMove();
    }

    public void exitGame(){
        mainStage.close();
    }

    private Scene loadScene(String pathToScene){
        Parent rootOfScene;
        try {
            loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource(pathToScene));
            rootOfScene = loader.load();
        }
        catch (IOException e) {
            Logger.log("Failed to load scene: " + pathToScene);
            mainStage.close();
            return null;
        }
        return new Scene(rootOfScene);
    }

    public void showAlert(String title,String header,String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);

        alert.setHeaderText(header);
        alert.setContentText(text);

        alert.showAndWait();
    }
}
