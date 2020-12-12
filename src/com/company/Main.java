package com.company;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("viewFile1.fxml"));
        primaryStage.setTitle("Курсовая работа");
        primaryStage.setScene(new Scene(root, 1083, 425));
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}
