package com.sql;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

import  


/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    StackPane stackPane = new StackPane();

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(stackPane, 640, 480);
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }

}