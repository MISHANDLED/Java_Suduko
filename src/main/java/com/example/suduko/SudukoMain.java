package com.example.suduko;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SudukoMain extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load((getClass().getResource("hello-view.fxml")));
        stage.setTitle("Hello World");
        stage.setScene(new Scene(root,300,275));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
