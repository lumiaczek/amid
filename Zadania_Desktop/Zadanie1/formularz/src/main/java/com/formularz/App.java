package com.formularz;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.util.regex.Pattern;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Formularz");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);

        Label nameLabel = new Label("Imię:");
        GridPane.setConstraints(nameLabel, 0, 0);
        TextField nameField = new TextField();
        nameField.setPromptText("Wprowadź imię");
        GridPane.setConstraints(nameField, 1, 0);

        Label lastNameLabel = new Label("Nazwisko:");
        GridPane.setConstraints(lastNameLabel, 0, 1);
        TextField lastNameField = new TextField();
        lastNameField.setPromptText("Wprowadź nazwisko");
        GridPane.setConstraints(lastNameField, 1, 1);

        Label addressLabel = new Label("Adres:");
        GridPane.setConstraints(addressLabel, 0, 2);
        TextField addressField = new TextField();
        addressField.setPromptText("Wprowadź adres");
        GridPane.setConstraints(addressField, 1, 2);

        Label cityLabel = new Label("Miejscowość:");
        GridPane.setConstraints(cityLabel, 0, 3);
        TextField cityField = new TextField();
        cityField.setPromptText("Wprowadź miejscowość");
        GridPane.setConstraints(cityField, 1, 3);

        Label phoneLabel = new Label("Telefon:");
        GridPane.setConstraints(phoneLabel, 0, 4);
        TextField phoneField = new TextField();
        phoneField.setPromptText("Wprowadź telefon");
        GridPane.setConstraints(phoneField, 1, 4);

        Label emailLabel = new Label("Email:");
        GridPane.setConstraints(emailLabel, 0, 5);
        TextField emailField = new TextField();
        emailField.setPromptText("Wprowadź email");
        GridPane.setConstraints(emailField, 1, 5);

        Label errorLabel = new Label();
        GridPane.setConstraints(errorLabel, 0, 6, 2, 1);

        Button submitButton = new Button("Zatwierdź");
        GridPane.setConstraints(submitButton, 0, 7);
        Button resetButton = new Button("Reset");
        GridPane.setConstraints(resetButton, 1, 7);

        grid.getChildren().addAll(
                nameLabel, nameField,
                lastNameLabel, lastNameField,
                addressLabel, addressField,
                cityLabel, cityField,
                phoneLabel, phoneField,
                emailLabel, emailField,
                errorLabel,
                submitButton, resetButton);

        // submitButton.disableProperty().bind(
        // nameField.textProperty().isEmpty()
        // .or(lastNameField.textProperty().isEmpty())
        // .or(addressField.textProperty().isEmpty())
        // .or(cityField.textProperty().isEmpty())
        // .or(phoneField.textProperty().isEmpty())
        // .or(emailField.textProperty().isEmpty())
        // .or(nameField.textProperty().matches("[a-zA-Z]{2,}").not())
        // .or(lastNameField.textProperty().matches("[a-zA-Z]{2,}").not())
        // .or(cityField.textProperty().matches("[a-zA-Z]{3,}").not())
        // .or(phoneField.textProperty().matches("\\d{9,}").not())
        // .or(emailField.textProperty().matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$").not())
        // );

        submitButton.setOnAction(e -> {
            errorLabel.setText("Formularz zatwierdzony!");
        });

        resetButton.setOnAction(e -> {
            nameField.clear();
            lastNameField.clear();
            addressField.clear();
            cityField.clear();
            phoneField.clear();
            emailField.clear();
            errorLabel.setText("");
        });

        Scene scene = new Scene(grid, 300, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
