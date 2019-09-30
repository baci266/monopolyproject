package application;

import application.model.Board;
import application.model.Game;
import application.model.Player;
import application.model.squares.OwnableSquare;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    public static final String URL = "jdbc:h2:~/monopoly2";
    public static final String USER_NAME = "sa";
    public static final String PASSWORD = "";

    private Connection connection;

    private Database() {
        try {
            this.connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        } catch (SQLException ex) {
            System.out.println("Chyba pri nacitani databazi: " + ex.getMessage());
        }
    }

    public static Game loadGame(int id_game){
        Game game = null;
        Database db = new Database();
        try {
            String query = "SELECT * FROM games WHERE id_game = ? ";
            var preparedStatement = db.connection.prepareStatement(query);
            preparedStatement.setInt(1, id_game);
            var result = preparedStatement.executeQuery();
            if (result.next()){
                String name = result.getString("name");
                int active_player = result.getInt("active_player");
                game = new Game(id_game, name, active_player);
            }
            else {
                return null;
            }

            List<Player> players = new ArrayList<>();
            query = "SELECT * FROM games_players WHERE id_game = ? ";
            preparedStatement = db.connection.prepareStatement(query);
            preparedStatement.setInt(1, id_game);
            result = preparedStatement.executeQuery();
            while (result.next()) {
                int id_player = result.getInt("id_player");
                String player_name = result.getString("name");
                int position = result.getInt("position");
                int money = result.getInt("money");
                int jail_time = result.getInt("jail_time");
                String icon = result.getString("icon");

                Player player = new Player(id_player, player_name, position, money, icon, jail_time);

                var query2 = "SELECT * FROM player_property WHERE id_game = ? AND id_player = ? ";
                var preparedStatement2 = db.connection.prepareStatement(query2);
                preparedStatement2.setInt(1, id_game);
                preparedStatement2.setInt(2, id_player);
                var result2 = preparedStatement2.executeQuery();
                List<OwnableSquare> properties = new ArrayList<>();
                while (result2.next()){
                    int property_position = result2.getInt("property_position");
                    String property_level = result2.getString("property_level");
                    OwnableSquare ownableSquare = (OwnableSquare) Board.getSquareByPosition(property_position);
                    ownableSquare.setLevel(property_level);
                    ownableSquare.setOwner(player);
                    properties.add(ownableSquare);
                }
                player.addProperties(properties);
                players.add(player);
            }
            game.setPlayers(players);
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return game;
    }

    public static void saveGame(Game game) {
        if (game.getIdGame() == -1){
            createGame(game);
        }
        else {
            updateGame(game);
        }
    }

    private static void updateGame(Game game) {
        Database db = new Database();
        String gamequery = "UPDATE games SET active_player = ? , date = NOW() WHERE id_game = ? ";
        try {
            var preparedStatement = db.connection.prepareStatement(gamequery);
            preparedStatement.setInt(1, game.getActivePlayer().getIdPlayer());
            preparedStatement.setInt(2, game.getIdGame());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        //zmazat property zavislosti
        String deleteProperties = "DELETE FROM player_property WHERE id_game = " + game.getIdGame();
        db.executeBaseQuery(deleteProperties);

        var players = game.getPlayers();
        for (Player player: players) {
            String query2 = "UPDATE games_players SET " +
                    "position = " + player.getPosition() + ", " +
                    "money = " + player.getMoney() + ", " +
                    "jail_time = " + player.getJailTime() + " " +
                    "WHERE id_player = " + player.getIdPlayer();
            db.executeBaseQuery(query2);

            var playerPropertySquares = player.getPropertySquares();
            for (OwnableSquare propertySquare: playerPropertySquares) {
                String query3 = "INSERT INTO player_property (id_game, id_player, property_position, property_level) VALUES ( ? , ? , ? , ? )";
                try {
                    var preparedStatement = db.connection.prepareStatement(query3, Statement.RETURN_GENERATED_KEYS);
                    preparedStatement.setInt(1, game.getIdGame());
                    preparedStatement.setInt(2, player.getIdPlayer());
                    preparedStatement.setInt(3, Board.getPositionBySquare(propertySquare));
                    preparedStatement.setString(4, propertySquare.getLevel());

                    db.executeInsertQuery(preparedStatement);

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        try {
            db.connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createGame(Game game){
        Database db = new Database();
        String query = "INSERT INTO games (name, active_player, date) VALUES ( ? , ? , NOW() )";
        int gameId = -1;
        try {
            var preparedStatement = db.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, game.gameName);
            preparedStatement.setInt(2, game.getActivePlayer().getIdPlayer());

            gameId = db.executeInsertQuery(preparedStatement);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        game.setIdGame(gameId);

        var players = game.getPlayers();
        for (Player player: players) {
            String query2 = "INSERT INTO games_players (id_game, name, position, money, jail_time, icon) VALUES ( ? , ? , ? , ? , ? , ?)";
            int playerId = -1;
            try {
               var preparedStatement2 = db.connection.prepareStatement(query2, Statement.RETURN_GENERATED_KEYS);
                preparedStatement2.setInt(1, gameId);
                preparedStatement2.setString(2, player.getName());
                preparedStatement2.setInt(3, player.getPosition());
                preparedStatement2.setInt(4, player.getMoney());
                preparedStatement2.setInt(5, player.getJailTime());
                preparedStatement2.setString(6, player.getIcon());

                playerId = db.executeInsertQuery(preparedStatement2);

            } catch (SQLException e) {
                e.printStackTrace();
            }

            player.setIdPlayer(playerId);

            var playerPropertySquares = player.getPropertySquares();
            for (OwnableSquare propertySquare : playerPropertySquares) {
                String query3 = "INSERT INTO player_property (id_game, id_player, property_position, property_level) VALUES ( ? , ? , ? , ? )";
                try {
                    var preparedStatement = db.connection.prepareStatement(query3, Statement.RETURN_GENERATED_KEYS);
                    preparedStatement.setInt(1, gameId);
                    preparedStatement.setInt(2, playerId);
                    preparedStatement.setInt(3, Board.getPositionBySquare(propertySquare));
                    preparedStatement.setString(4, propertySquare.getLevel());

                    db.executeInsertQuery(preparedStatement);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        String gamequery = "UPDATE games SET active_player = ? WHERE id_game = ? ";
        try {
            var preparedStatement = db.connection.prepareStatement(gamequery);
            preparedStatement.setInt(1, game.getActivePlayer().getIdPlayer());
            preparedStatement.setInt(2, gameId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static  List<GameToLoad> getGamesToLoad() {
        List<GameToLoad> gamesToLoad = new ArrayList<>();

        Database db = new Database();
        String query = "SELECT * FROM games";
        ResultSet rs = db.executeSelectQuery(query);
        try {
            while (rs.next()) {
                int id = rs.getInt("id_game");
                String name = rs.getString("name");
                String date = rs.getString("date");

                String query2 = "SELECT COUNT(*) as total FROM games_players WHERE id_game = " + id;
                ResultSet rs2 = db.executeSelectQuery(query2);
                rs2.next();
                int playersCount = rs2.getInt("total");
                gamesToLoad.add(new GameToLoad(id, name, date, playersCount));
            }
            db.connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return gamesToLoad;
    }


   public static void initDatabase (){
       Database db = new Database();
       db.createGamesPlayerTable();
       db.createGameTable();
       db.createPlayerGameProperty();
       try {
           db.connection.close();
       } catch (SQLException e) {
           e.printStackTrace();
       }
   }

    //region Create tables in database
    private void createGamesPlayerTable(){
        String query = "CREATE TABLE IF NOT EXISTS games_players (" +
                "id_player INT NOT NULL AUTO_INCREMENT, " +
                "id_game INT NOT NULL, " +
                "name VARCHAR(45) NOT NULL, " +
                "position INT, " +
                "money INT, " +
                "jail_time INT, " +
                "icon VARCHAR(45), " +
                "PRIMARY KEY (id_player))";
        executeBaseQuery(query);
    }
    private void createPlayerGameProperty() {
        String query = "CREATE TABLE IF NOT EXISTS player_property (" +
                "id INT NOT NULL AUTO_INCREMENT, " +
                "id_game INT NOT NULL, " +
                "id_player INT NOT NULL, " +
                "property_position INT NOT NULL, " +
                "property_level VARCHAR(10), " +
                "PRIMARY KEY (id))";
        executeBaseQuery(query);
    }
    private void createGameTable() {
        String query = "CREATE TABLE IF NOT EXISTS games (" +
                "id_game INT NOT NULL AUTO_INCREMENT, " +
                "name VARCHAR(45) NOT NULL, " +
                "active_player INT, " +
                "date DATETIME, " +
                "PRIMARY KEY (id_game))";
        executeBaseQuery(query);
    }
    //endregion

    /**
     * executes query and logs error
     * @param query
     */
    private void executeBaseQuery(String query) {
        try {
            var statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());        }
    }

    /**
     * @param query
     * @return id of row in table | -1 if not exist
     */
    private int existInTable(String query){
        try {
            var statement = connection.createStatement();
            var res = statement.executeQuery(query);
            return res.getInt("id");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }

    /**
     *
     * @param ps
     * @return id of insert
     */
    private int executeInsertQuery(PreparedStatement ps){
        try {
            ps.executeUpdate();
            var res = ps.getGeneratedKeys();
            if (res.next()) {
                return res.getInt(1);
            } else  {
                return -1;
            }
        } catch (SQLException e) {
            System.out.println("Failed to Insert query: " + ps + " with error: " +e.getMessage());
            return -1;
        }
    }

    private ResultSet executeSelectQuery(String query){
        try {
            var statement = connection.createStatement();
            return statement.executeQuery(query);
        } catch (SQLException e) {
            System.out.println("Failed to Insert query: " + query + " with error: " +e.getMessage());
            return null;
        }
    }
}
