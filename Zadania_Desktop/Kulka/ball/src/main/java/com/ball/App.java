package com.ball;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class App extends Application {

    private static final double BALL_RADIUS = 15;
    private static final double WINDOW_WIDTH = 800;
    private static final double WINDOW_HEIGHT = 600;
    private double dx = 3; // Prędkość w poziomie
    private double dy = 3; // Prędkość w pionie

    @Override
    public void start(Stage primaryStage) {
        Pane pane = new Pane();

        Circle ball = new Circle(BALL_RADIUS, Color.RED);
        ball.setCenterX(BALL_RADIUS);
        ball.setCenterY(BALL_RADIUS);
        pane.getChildren().add(ball);

        Timeline animation = new Timeline(new KeyFrame(Duration.millis(20), e -> moveBall(ball)));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();

        Scene scene = new Scene(pane, WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setTitle("Pilka");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void moveBall(Circle ball) {
        ball.setCenterX(ball.getCenterX() + dx);
        ball.setCenterY(ball.getCenterY() + dy);

        if (ball.getCenterX() - BALL_RADIUS < 0 || ball.getCenterX() + BALL_RADIUS > WINDOW_WIDTH) {
            dx *= -1;
        }

        if (ball.getCenterY() - BALL_RADIUS < 0 || ball.getCenterY() + BALL_RADIUS > WINDOW_HEIGHT) {
            dy *= -1;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
