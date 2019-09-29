package application;

import application.model.Player;
import javafx.scene.control.ListView;

public class Logger {
    public static ListView<String> listView;

    public static void log(String message){
        System.out.println(message);
        listView.getItems().add(0, message);
    }

    public static void logPlayerActivity(Player activePlayer, String message) {
        String string = activePlayer.getName() + " " + message;
        System.out.println(string);
        listView.getItems().add(0, string);
    }
}
