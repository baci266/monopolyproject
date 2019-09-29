package application;

import application.model.Player;

public class Logger {
    public static void log(String message){
        System.out.println(message);
    }

    public static void logPlayerActivity(Player activePlayer, String message) {
        System.out.println(activePlayer.getName() + " " + message);
    }
}
