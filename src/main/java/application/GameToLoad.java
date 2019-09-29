package application;

public class GameToLoad {
    int id;
    String name;
    String date;
    int players; // pocet hracov v hre

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPlayers() {
        return players;
    }

    public void setPlayers(int players) {
        this.players = players;
    }

    public GameToLoad(int id, String name, String date, int players) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.players = players;
    }
}
