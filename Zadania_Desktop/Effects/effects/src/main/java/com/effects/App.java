package com.effects;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import javax.swing.text.html.ImageView;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        // Creating an image
        Image image = new Image("https://www.tutorialspoint.com/green/images/logo.png");

        // Setting the image view
        ImageView imageView = new ImageView(image);

        // Setting the position of the image
        imageView.setX(100);
        imageView.setY(70);

        // setting the fit height and width of the image view
        imageView.setFitHeight(200);
        imageView.setFitWidth(400);

        // Setting the preserve ratio of the image view
        imageView.setPreserveRatio(true);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}