package com.zegar;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.text.SimpleDateFormat;
import java.util.Date;

public class App extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Zegar");

        Label countdownLabel = new Label();
        Label currentTimeLabel = new Label();

        VBox root = new VBox(10);
        root.getChildren().addAll(countdownLabel, currentTimeLabel);

        Scene scene = new Scene(root, 300, 150);
        primaryStage.setScene(scene);

        Timeline countdownTimeline = new Timeline(new KeyFrame(
                Duration.seconds(1),
                event -> {
                    long currentTimeMillis = System.currentTimeMillis();
                    long targetTimeMillis = getTargetTimeMillis();
                    long remainingMillis = targetTimeMillis - currentTimeMillis;

                    if (remainingMillis > 0) {
                        long seconds = remainingMillis / 1000 % 60;
                        long minutes = remainingMillis / (60 * 1000) % 60;
                        long hours = remainingMillis / (60 * 60 * 1000) % 24;
                        long days = remainingMillis / (24 * 60 * 60 * 1000);

                        countdownLabel.setText(
                                String.format("Odliczanie: %02d days, %02d:%02d:%02d", days, hours, minutes, seconds));
                    } else {
                        countdownLabel.setText("Nowy rok!");
                    }
                }));
        countdownTimeline.setCycleCount(Animation.INDEFINITE);
        countdownTimeline.play();

        Timeline currentTimeTimeline = new Timeline(new KeyFrame(
                Duration.seconds(1),
                event -> {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String currentTime = dateFormat.format(new Date());
                    currentTimeLabel.setText("Obecna godzina: " + currentTime);
                }));
        currentTimeTimeline.setCycleCount(Animation.INDEFINITE);
        currentTimeTimeline.play();

        primaryStage.show();
    }

    private long getTargetTimeMillis() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date targetDate = dateFormat.parse("2025-01-01 00:00:00");
            return targetDate.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
