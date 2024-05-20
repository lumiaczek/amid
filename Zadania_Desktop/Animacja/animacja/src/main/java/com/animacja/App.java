package com.animacja;

import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {

        BorderPane root = new BorderPane();
        FlowPane buttons = new FlowPane();

        Button start = new Button("Start");
        Button stop = new Button("Stop");
        Button autor = new Button("Autor");

        buttons.getChildren().addAll(start, stop, autor);
        
        Rectangle rectangle = new Rectangle();
        Image img = new Image(getClass().getResourceAsStream("cat.png"));
        rectangle.setFill(new ImagePattern(img));

        rectangle.setWidth(200.0f); 
        rectangle.setHeight(200.0f);  

        buttons.setAlignment(Pos.CENTER);

        RotateTransition rotateTransition = new RotateTransition();

        rotateTransition.setDuration(Duration.millis(800));
        rotateTransition.setNode(rectangle);
        rotateTransition.setByAngle(360); 
        rotateTransition.setCycleCount(RotateTransition.INDEFINITE); 
        rotateTransition.setAutoReverse(false); 
        
        start.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent arg0) {
                rotateTransition.play();
            }
            
        });

        stop.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                rotateTransition.stop();
            }
            
        });

        autor.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
               Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Autor");
                alert.setHeaderText("Daniel Borowski");

                alert.showAndWait();
            }
            
        });

        root.setCenter(rectangle);
        root.setMargin(buttons, new Insets(12,12,12,12));
        root.setBottom(buttons);

        scene = new Scene(root, 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}