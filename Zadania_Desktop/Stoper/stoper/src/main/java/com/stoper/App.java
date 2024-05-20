package com.stoper;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class App extends Application {

    private boolean running = false;
    private long startTime;
    private long elapsedTime = 0;

    private Label timeLabel = new Label("00:00:00.000");
    private ListView<String> loopListView = new ListView<>();
    private List<String> loops = new ArrayList<>();
    private Thread timerThread;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Button startBtn = new Button("Start");
        Button stopBtn = new Button("Stop");
        Button resetBtn = new Button("Reset");
        Button loopBtn = new Button("Loop");

        startBtn.setOnAction(e -> startTimer());
        stopBtn.setOnAction(e -> stopTimer());
        resetBtn.setOnAction(e -> resetTimer());
        loopBtn.setOnAction(e -> recordLoop());

        FlowPane buttony = new FlowPane(startBtn, stopBtn, resetBtn, loopBtn);

        VBox root = new VBox(10, timeLabel, buttony, loopListView);
        Scene scene = new Scene(root, 300, 400);

        primaryStage.setTitle("Stopwatch");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void startTimer() {
        if (!running) {
            running = true;
            startTime = System.currentTimeMillis() - elapsedTime;

            timerThread = new Thread(() -> {
                while (running) {
                    elapsedTime = System.currentTimeMillis() - startTime;
                    Platform.runLater(() -> timeLabel.setText(formatTime(elapsedTime)));
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
            timerThread.setDaemon(true);
            timerThread.start();
        }
    }

    private void stopTimer() {
        running = false;
    }

    private void resetTimer() {
        running = false;
        elapsedTime = 0;
        timeLabel.setText("00:00:00.000");
        loops.clear();
        loopListView.getItems().clear();
    }

    private void recordLoop() {
        if (running) {
            loops.add(formatTime(elapsedTime));
            loopListView.getItems().setAll(loops);
        }
    }

    private String formatTime(long elapsedTime) {
        int hours = (int) (elapsedTime / 3600000);
        int minutes = (int) ((elapsedTime % 3600000) / 60000);
        int seconds = (int) ((elapsedTime % 60000) / 1000);
        int millis = (int) (elapsedTime % 1000);
        return String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, millis);
    }
}
