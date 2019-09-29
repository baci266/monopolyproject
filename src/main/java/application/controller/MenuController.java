package application.controller;

import application.Database;
import application.GameToLoad;
import application.model.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.*;

public class MenuController  implements Initializable,Controllable {
    //TODO: pridat select pocet hracov
    //TODO: podla poctu hracov vytvorit policka pre hracov spravit na to novu triedu
    //TODO: a bude spolocny list a bude sa vymazavat ak si jeden hrac vyberie

    private MainController mainController;

    private ObservableList<String> icons;

    private Map<TextField,ChoiceBox<String>> playerInformation;

    //region Elements
    @FXML
    private TableView<GameToLoad> gamesTable;

    @FXML
    private TextField gameTitle;

    @FXML
    private TextField player1Name;

    @FXML
    private TextField player2Name;

    @FXML
    private TextField player3Name;

    @FXML
    private TextField player4Name;

    @FXML
    private ChoiceBox<String> player1Icon;

    @FXML
    private ChoiceBox<String> player2Icon;

    @FXML
    private ChoiceBox<String> player3Icon;

    @FXML
    private ChoiceBox<String> player4Icon;
    //endregion

    //region Actions
    @FXML
    void startGame(ActionEvent event){
        List<Player> players = createPlayers();
        if(players == null){
            mainController.showAlert("Error",null,"Wrong Data");
        }
        else {
            mainController.startGame(gameTitle.getText(), players);
        }
    }

    @FXML
    void quitGame(ActionEvent event) {
        mainController.exitGame();
    }
    @FXML
    void loadGame(ActionEvent event) {
        if (gamesTable.getSelectionModel().getSelectedItem() != null) {
            GameToLoad selectedGame = gamesTable.getSelectionModel().getSelectedItem();
            int gameId = selectedGame.getId();
            mainController.loadGame(gameId);
        }
        else {
            mainController.showAlert("Chyba", "", "Select game to load");
        }
    }

    //endregion

    public void setMainController(MainController mainController){
        this.mainController = mainController;
        createGamesToLoad();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gamesTable.setPlaceholder(new Label("No games played"));

        TableColumn<GameToLoad, String> idColumn = new TableColumn<>("Id");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idColumn.setPrefWidth(55);

        TableColumn<GameToLoad, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setPrefWidth(225);

        TableColumn<GameToLoad, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        dateColumn.setPrefWidth(170);

        TableColumn<GameToLoad, String> playersColumn = new TableColumn<>("Players");
        playersColumn.setCellValueFactory(new PropertyValueFactory<>("players"));
        playersColumn.setPrefWidth(75);

        gamesTable.getColumns().add(idColumn);
        gamesTable.getColumns().add(nameColumn);
        gamesTable.getColumns().add(dateColumn);
        gamesTable.getColumns().add(playersColumn);

        List<String> list = new ArrayList<>();
        playerInformation = new HashMap<>();
        playerInformation.put(player1Name,player1Icon);
        playerInformation.put(player2Name,player2Icon);
        playerInformation.put(player3Name,player3Icon);
        playerInformation.put(player4Name,player4Icon);

        list.add("Boot");
        list.add("Car");
        list.add("Hat");
        list.add("Iron");

        icons = FXCollections.observableArrayList(list);

        player1Icon.setItems(icons);
        player2Icon.setItems(icons);
        player3Icon.setItems(icons);
        player4Icon.setItems(icons);
    }

    private List<Player> createPlayers(){
        List<Player> players = new ArrayList<>();
        playerInformation.forEach((k,v)->
        {
            Player player = createPlayer(k,v);
            if (player != null)
                players.add(player);

        });

        if (players.size()>1){
            return players;
        }
        else return null;

    }

    private Player createPlayer(TextField playerName,ChoiceBox<String> playerIcon){
        if(playerName.getText().isEmpty() || playerIcon.getValue() == null){
            return null;
        }
        else
            return new Player(playerName.getText(),playerIcon.getValue());
    }

    private void createGamesToLoad(){
        List<GameToLoad> gamesToLoad = new ArrayList<>();

        gamesToLoad = Database.getGamesToLoad();
        gamesTable.getItems().addAll(gamesToLoad);


    }
}