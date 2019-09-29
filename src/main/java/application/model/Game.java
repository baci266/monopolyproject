package application.model;

import application.Logger;

import java.util.List;

public class Game {
    private int id_game = -1;
    List<Player> players;
    public String gameName;
    public Board board;
    Dice dice = new Dice();

    Player activePlayer;
    int activePlayerId = -1;

    public Game(List<Player> players, String gameName) {
        this.players = players;
        this.gameName = gameName;
        this.board = new Board();
    }
    public Game(int id_game, String gameName, int activePlayerId) {
        this.id_game = id_game;
        this.gameName = gameName;
        this.activePlayerId = activePlayerId;
        this.board = new Board();
    }

    public int getIdGame() {
        return id_game;
    }

    public void setIdGame(int gameId) {
        id_game = gameId;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public String getGameName() {
        return gameName;
    }

    public Board getBoard() {
        return board;
    }

    public Dice getDice() {
        return dice;
    }

    public void deletePlayer(Player player){
       players.remove(player);
    }

    public void setNextActivePlayer(){
        if(activePlayer == null && activePlayerId == -1){
            activePlayer = players.get(0);
        }
        else if (activePlayerId != -1 && activePlayer == null) {
            activePlayer = players.stream().filter(player -> player.getIdPlayer() == activePlayerId).findFirst().orElse(null);
        }
        else {
            int actualPlayerPosition = players.indexOf(activePlayer);
            if (actualPlayerPosition < players.size() - 1) {
                activePlayer = players.get(actualPlayerPosition + 1);
            } else
                activePlayer = players.get(0);
        }
        Logger.log(String.format("Active Player is: \"%s\" ", activePlayer.getName()));

    }

    public void setActivePlayer(Player player){
        activePlayer = player;
    }

    public Player getActivePlayer() {
        return activePlayer;
    }
}
