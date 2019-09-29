package application.controller;

import application.Database;
import application.Logger;
import application.View;
import application.model.Board;
import application.model.Game;
import application.model.Player;
import application.model.squares.*;
import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.util.Duration;


import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class GameController implements Controllable{

    public MainController mainController;
    private boolean isRunning;

    //region ===========Elements===============
    @FXML
    public AnchorPane mainPane;
    @FXML
    public ImageView dice1;

    @FXML
    public ImageView dice2;

    @FXML
    public Button sellButton;

    @FXML
    private HBox actionHBox;

    @FXML
    private Label activePlayerLabel;

    @FXML
    private Label scoreBoardLabel;

    @FXML
    private GridPane gameBoard;

    @FXML
    private AnchorPane playerInfoPane;

    @FXML
    public Button rollDiceButton;

    @FXML
    private AnchorPane boardAnchor;

    @FXML
    private VBox scoresBox;

    @FXML
    private AnchorPane scorePane;

    @FXML
    private VBox information;

    @FXML
    public Button endTurnButton;

    @FXML
    public Button buyButton;

    @FXML
    public Button upgradeButton;

    @FXML
    private Button startButton;

    @FXML
    private Button exitButton;

    @FXML
    private Button saveButton;

    @FXML
    private ListView<String> listView;
    //endregion

    @FXML
    void startGameAction(ActionEvent event) {
        List<Player> players = this.mainController.game.getPlayers();
        gameBoard.getChildren().addAll(players);
        for (Player player:players) {
            movePlayer(player, 1);
        }
        processBeforeMove();
        //after click on start game disable startGameButton
        disableButtons(startButton);
    }

    @FXML
    void saveGameAction(ActionEvent event) {
        Database.saveGame(mainController.game);
        mainController.showAlert("INFO", "Game " + mainController.game.gameName + " was saved","");
    }

    @FXML
    void exitGame(ActionEvent event) {
        mainController.exitGame();
    }
    @FXML
    void rollDice(ActionEvent event) {
        processMove();
    }

    @FXML
    void buy(ActionEvent event) {
        Game game = mainController.game;
        Player activePlayer = game.getActivePlayer();
        Square square = game.getBoard().getSquareByPosition(activePlayer.getPosition());
        activePlayer.buyProperty(square);
        disableButtons(buyButton);
    }

    @FXML
    void sell(ActionEvent event) {

    }

    @FXML
    void upgrade(ActionEvent event) {

    }

    @FXML
    void endTurn(ActionEvent event) {
        Game game = mainController.game;
        Logger.log(String.format("Player: \"%s\" ends his turn.", game.getActivePlayer().getName()));
        processBeforeMove();
    }

    private void processMove(){
        Game game = this.mainController.game;
        Player activePlayer = game.getActivePlayer();

        disableButtons(rollDiceButton);
        enableButtons(endTurnButton);
        int dice1 = game.getDice().rollFirstDice();
        int dice2 = game.getDice().rollSecondDice();
        this.dice1.setImage(new Image(View.PATH_TO_IMAGES + "dice" + dice1 + ".png"));
        this.dice2.setImage(new Image(View.PATH_TO_IMAGES + "dice" + dice2 + ".png"));
        int distance = dice1 + dice2;
        activePlayer.makeMove(distance);
        movePlayer(activePlayer, distance);
        Logger.log(String.format("Player: \"%s\" move to position %d", activePlayer.getName(), activePlayer.getPosition()));
        Square square = game.getBoard().getSquareByPosition(activePlayer.getPosition());
        square.processPlayer(activePlayer,this);

        if(activePlayer.hasProperties()){
            enableButtons(sellButton);
        }
    }

    public void processBeforeMove(){
        disableAllButtons();
        mainController.game.setNextActivePlayer();
        updateActivePlayerText();
        Player activePlayer = mainController.game.getActivePlayer();
        if (activePlayer.isInJail()) {
            enableButtons(endTurnButton);
        }
        else {
            enableButtons(rollDiceButton);
        }
    }

    private void disableAllButtons(){
        rollDiceButton.setDisable(true);
        buyButton.setDisable(true);
        sellButton.setDisable(true);
        upgradeButton.setDisable(true);
        endTurnButton.setDisable(true);
    }

    private void disableButtons(Button ... buttons){
        for (Button b: buttons) {
            b.setDisable(true);
        }
    }

    public void enableButtons(Button ... buttons){
        for (Button b: buttons) {
            b.setDisable(false);
        }
    }

    private List<Label> playerscoreBoard;

    private void createPlayerScoreBoard(int howmany){
        playerscoreBoard = new ArrayList<>();
        for (int i = 0; i < howmany; i++) {
            Label label = new Label();
            playerscoreBoard.add(label);
            scoresBox.getChildren().add(label);
        }
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;

        Logger.listView = listView;

        populateBoard();
        isRunning = true;
        Thread updater = new Thread(this::update);
        updater.setDaemon(true);
        createPlayerScoreBoard(mainController.game.getPlayers().size());
        updater.start();
        disableAllButtons();
    }

    private void populateBoard(){
        Board board = this.mainController.game.board;
        ArrayList<Square> squares = board.getSquares();

        for (int i = 0; i < squares.size(); i++) {
            Square square = squares.get(i);

            square.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            square.getStyleClass().add("square");
            if (i < 10) {
                gameBoard.add(square, 10 -i, 10);
            } else if (i < 20) {
                gameBoard.add(square, 0, 20 - i);
            } else if (i < 30) {
                gameBoard.add(square, i - 20, 0);
            } else if (i < 40) {
                gameBoard.add(square, 10, i - 30);
            }
        }
    }

    private void movePlayer(Player player,int distance){

        int to = player.getPosition();
        int from = to - distance;
        List<Square> squares = this.mainController.game.getBoard().getSquaresFromTo(from,to);
        double [] positions = new double[squares.size()*2];
        for (int i = 0; i < squares.size()*2; i += 2) {
            Square s = squares.get(i/2);

            positions[i] = s.getCenterX();
            positions[i+1] = s.getCenterY();
        }
        PathTransition pathTransition = new PathTransition(Duration.seconds((float)distance/3),new Polyline(positions),player);
        pathTransition.play();

        Object o = new Object();
        pathTransition.setOnFinished(e -> {
            synchronized (o){
                o.notify();
            }
        });
    }

    public void movePlayerToJail(Player player){
        Board board = this.mainController.game.getBoard();
        Square jail = board.getJailSquare();
        Square from = board.getSquareByPosition(player.getPosition());
        PathTransition pathTransition = new PathTransition(
                Duration.seconds(2),
                new Line(from.getCenterX(),from.getCenterY(),jail.getCenterX(),jail.getCenterY()),
                player);
        pathTransition.play();
    }

    private void updateActivePlayerText() {
        if (this.mainController.game.getActivePlayer() != null) {
            activePlayerLabel.setText(this.mainController.game.getActivePlayer().name);
        }
    }

    private void updateScoreBoard(){
        List<Player> sortedPlayers = this.mainController.game.getPlayers()
                .stream().sorted(Comparator.comparing(Player::getMoney))
                .collect(Collectors.toList());
        for (int i = 0; i < playerscoreBoard.size(); i++) {
            try {
                Player player = sortedPlayers.get(i);
                playerscoreBoard.get(i).setText(player.getName() + ":  " + player.getMoney());
            } catch (IndexOutOfBoundsException e) {
                playerscoreBoard.get(i).setText("");
            }
        }
    }

    private void update(){
        while (isRunning){
            Platform.runLater(() ->
                    updateScoreBoard());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) { }
        }
    }

    public void showCardDialog(String description) {
        mainController.showAlert("Card description", description,"");
    }
}
