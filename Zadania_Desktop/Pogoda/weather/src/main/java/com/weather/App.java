package com.weather;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Flow;

import org.json.JSONObject;
import org.json.JSONTokener;

public class App extends Application {

    private static Scene scene;
    JSONObject pogoda;

    @Override
    public void start(Stage stage) throws IOException {

        TextField wyszukiwanie = new TextField("");
        wyszukiwanie.setPromptText("Warszawa");
        wyszukiwanie.setPrefWidth(550);

        Text title = new Text("Weather API");
        title.setId("title");

        StackPane cityNameWrapper = new StackPane();
        cityNameWrapper.setAlignment(Pos.CENTER);

        Text cityName = new Text();

        FlowPane dane = new FlowPane();
        dane.setOrientation(Orientation.VERTICAL);
        dane.setVgap(2d);
        dane.setPadding(new Insets(5));

        Image lupa = new Image(getClass().getResourceAsStream("lupa.png"));
        Button szukaj_btn = new Button("", new ImageView(lupa));

        StackPane rootPane = new StackPane(); // główny layout
        rootPane.setPadding(new Insets(10));
        rootPane.setId("root");

        StackPane header = new StackPane(); // nagłówek

        header.getChildren().addAll(title);
        header.setAlignment(Pos.TOP_CENTER);
        header.setPadding(new Insets(2));

        GridPane gp = new GridPane(); // pasek wyszukiwania

        gp.addColumn(0, wyszukiwanie);
        gp.addColumn(1, szukaj_btn);
        gp.setHgap(5d);
        gp.setVgap(10d);
        gp.setPadding(new Insets(60));
        gp.setAlignment(Pos.TOP_CENTER);

        GridPane gp2 = new GridPane(); // dane po wyszukiwaniu
        gp2.setHgap(5d);
        gp2.setVgap(10d);
        gp2.setPadding(new Insets(20));
        gp2.setAlignment(Pos.TOP_CENTER);
        gp2.addColumn(0, cityNameWrapper, dane);

        gp.addRow(1, gp2);
        GridPane.setColumnSpan(gp2, 2);

        EventHandler<ActionEvent> curl = new EventHandler<ActionEvent>() { // wyszukanie po url
            public void handle(ActionEvent e) {
                try {

                    dane.getChildren().clear(); // czyszczenie danych
                    cityNameWrapper.getChildren().clear();

                    URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + wyszukiwanie.getText()
                            + "&appid=1cebc96e10a7eecdc40c25f8aba1b378&lang=pl");

                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestProperty("accept", "application/json");

                    BufferedReader responseBuffer = new BufferedReader( // odczytanie jsona
                            new InputStreamReader(connection.getInputStream()));
                    JSONTokener jsonTokener = new JSONTokener(responseBuffer);
                    pogoda = new JSONObject(jsonTokener);

                    String icon = pogoda.getJSONArray("weather").getJSONObject(0).getString("icon"); // wyszukiwanie
                                                                                                     // obrazka

                    Image iconImage = new Image("https://openweathermap.org/img/wn/" + icon + "@2x.png"); // obrazek
                    ImageView iconView = new ImageView(iconImage);

                    cityNameWrapper.getChildren().addAll(cityName); // nazwa miasta
                    cityName.setText(wyszukiwanie.getText() + ", "
                            + pogoda.getJSONArray("weather").getJSONObject(0).getString("description"));
                    cityName.setTextAlignment(TextAlignment.CENTER);
                    cityName.setId("city");

                    float temp = pogoda.getJSONObject("main").getFloat("temp"); // temperatura
                    String tmp = String.valueOf(((int) Math.floor(temp - 273)) + "°");
                    Text temperatura = new Text(tmp);
                    temperatura.setId("temp");

                    FlowPane tempPog = new FlowPane();
                    tempPog.getChildren().clear();
                    tempPog.setOrientation(Orientation.HORIZONTAL);
                    tempPog.setAlignment(Pos.CENTER);
                    tempPog.setHgap(12d);
                    tempPog.getChildren().addAll(iconView, temperatura);

                    // zbieranie danych

                    // temperatura
                    float odcz = pogoda.getJSONObject("main").getFloat("feels_like"); // temperatura odczuwalna
                    String odcz_ = String.valueOf(((int) Math.floor(odcz - 273)) + "°");
                    Text odczuwalna = new Text("Odczuwalna: " + odcz_);

                    float max = pogoda.getJSONObject("main").getFloat("temp_max"); // temperatura odczuwalna
                    String max_ = String.valueOf(((int) Math.floor(max - 273)) + "°");
                    Text maksimum = new Text("Maksymalna: " + max_);

                    float min = pogoda.getJSONObject("main").getFloat("temp_min"); // temperatura odczuwalna
                    String min_ = String.valueOf(((int) Math.floor(min - 273)) + "°");
                    Text minimalna = new Text("Minimalna: " + min_);

                    // ciśnienie

                    int presu = pogoda.getJSONObject("main").getInt("pressure"); // ciśnienie
                    String presu_ = String.valueOf((presu)) + " hPa";
                    Text pressure = new Text("Ciśnienie: " + presu_);

                    // wilgotność

                    int hum = pogoda.getJSONObject("main").getInt("humidity"); // wilgotność
                    String hum_ = String.valueOf((hum)) + "%";
                    Text humidity = new Text("Wilgotność: " + hum_);

                    // wiatr

                    float wind = pogoda.getJSONObject("wind").getFloat("speed"); // prędkość wiatru
                    String wind_ = String.valueOf((wind)) + " m/s";
                    Text wind_speed = new Text("Prędkość wiatru: " + wind_);

                    // formater

                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

                    // wschód

                    long sunr = pogoda.getJSONObject("sys").getLong("sunrise"); // wschód słońca
                    Date sunr_ = new Date(sunr);
                    Text sunrise = new Text("Wschód słońca: " + sdf.format(sunr_));

                    long suns = pogoda.getJSONObject("sys").getLong("sunset"); // wschód słońca
                    Date suns_ = new Date(suns);
                    Text sunset = new Text("Zachód słońca: " + sdf.format(suns_));

                    GridPane info = new GridPane();
                    info.getChildren().clear();
                    info.setHgap(10d);
                    info.setVgap(10d);
                    info.setPadding(new Insets(20));
                    info.setAlignment(Pos.CENTER);
                    info.addColumn(0, odczuwalna, maksimum, minimalna);
                    info.addColumn(1, pressure, humidity, wind_speed);
                    info.addColumn(2, sunrise, sunset);

                    dane.getChildren().addAll(tempPog, info);

                } catch (IOException e1) {
                    cityNameWrapper.getChildren().addAll(cityName);
                    cityName.setText("Miasta nie znaleziono");
                }
            }
        };

        szukaj_btn.setOnAction(curl); // wyszukaj

        rootPane.getChildren().addAll(header, gp); // główny layout
        Scene scene = new Scene(rootPane, 600, 500);
        scene.getStylesheets().add(App.class.getResource("style.css").toExternalForm());
        stage.setTitle("Weather API");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}