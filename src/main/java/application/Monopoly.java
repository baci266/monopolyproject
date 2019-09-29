package application;

import application.controller.MainController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Monopoly extends Application {

    @Override
    public void start(Stage primaryStage) {
        Runtime.getRuntime().addShutdownHook(new Thread(() ->{ System.out.println("closing");}));
        Database.initDatabase();
        MainController mainController = new MainController(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
