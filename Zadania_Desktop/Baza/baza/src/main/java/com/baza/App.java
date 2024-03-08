package com.baza;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.io.IOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;



public class App extends Application {

    public class poleTabeli{
        
        String nazwa, typ;

        poleTabeli(String nazwa_, String typ_){
            nazwa = nazwa_;
            typ = typ_;
        }

        public String getNazwa() {
            return nazwa;
        }

        public String getTyp(){
            return typ;
        }
        


    } 
    int columnCount = 0;

    VBox vbox1;
    VBox vbox2;
    VBox vbox3;

    BorderPane borderPane;

    int poleCounter = 0;

    String exmplQuery = "SELECT * FROM amid";
    String connUrl = "jdbc:mysql://localhost:3306/amid";


    private Scene scene;

    @SuppressWarnings("unchecked")
    @Override
    public void start(Stage stage) throws IOException {

        stage.setTitle("Testowanie");
        vbox1 = dodajTabele();

        ComboBox comboBox = new ComboBox();

        comboBox.getItems().add("Dodaj tabele");
        comboBox.getItems().add("Dodaj rekord");
        comboBox.getItems().add("Wyszukaj rekord");

        comboBox.getSelectionModel().select(0);

        comboBox.setOnAction((event) -> {
            int selectedIndex = comboBox.getSelectionModel().getSelectedIndex();
        
            switch (selectedIndex) {
                case 0:
                    vbox1 = dodajTabele();
                    switchPanes(vbox1);
                    break;
                
                case 1:
                    vbox2 = dodajRekord();
                    switchPanes(vbox2);
                    break;

                case 2:
                    vbox3 = wyszukajRekord();
                    switchPanes(vbox3);
                    break;
            
                default:
                    break;
            }
        });

        HBox hbox = new HBox(comboBox);

        borderPane = new BorderPane();
        borderPane.setTop(hbox);
        borderPane.setCenter(vbox1);
        borderPane.setPadding(new Insets(20, 20, 20, 20));

        scene = new Scene(borderPane, 600, 600);

        stage.setScene(scene);
        stage.show();


    }

    // ! ------------------ Dodawanie tabeli ---------------------

    private VBox dodajTabele() {

        Text tekst = new Text("Dodawanie tabeli");

        vbox1 = new VBox(tekst);
        vbox1.setPadding(new Insets(10, 10, 10, 10));

        FlowPane nazwaPane = new FlowPane();

        TextField nazwa = new TextField();
        Text nazwaTabeli = new Text("Nazwa tabeli:");

        nazwaPane.getChildren().addAll(nazwaTabeli, nazwa);        
        
        Button dodaj = new Button("Dodaj Pole");
        Button dodajTabele = new Button("Dodaj Tabele");

        FlowPane buttons = new FlowPane(dodaj, dodajTabele);
        
        vbox1.getChildren().addAll(nazwaPane, buttons);


        List<poleTabeli> pola = new ArrayList<poleTabeli>();


        dodaj.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                TextField nazwaPola = new TextField();
                ComboBox comboBox = new ComboBox();
                Button zatwierdz = new Button("Zatwierdź");

                comboBox.getItems().add("int");
                comboBox.getItems().add("varchar(200)");

                comboBox.getSelectionModel().select(0);

                FlowPane dodawaniePola = new FlowPane();
                dodawaniePola.getChildren().addAll(nazwaPola, comboBox, zatwierdz);
                vbox1.getChildren().addAll(dodawaniePola);

                zatwierdz.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent arg0) {

                        zatwierdz.setDisable(true);

                        nazwaPola.setEditable(false);
                        comboBox.setDisable(true);

                        poleTabeli pole = new poleTabeli(nazwaPola.getText(), comboBox.getSelectionModel().getSelectedItem().toString());

                        pola.add(pole);

                        Text numerPola = new Text(Integer.toString(poleCounter));

                        poleCounter++;

                    Button usun = new Button("Usuń");

                    dodawaniePola.getChildren().addAll(usun, numerPola);

                    usun.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent arg0) {
                            pola.remove(Integer.parseInt(numerPola.getText()));
                            poleCounter--;  
                            dodawaniePola.getChildren().clear();
                        }
                        
                    });

                    }
                    
                });



            }

            
        });
        
        dodajTabele.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {

                StringBuilder query = new StringBuilder();

                query.append("CREATE TABLE " + nazwa.getText() + " (");
                
                for(int i = 0; i < poleCounter ; i++) {
                    query.append(pola.get(i).nazwa);
                    query.append(" ");
                    query.append(pola.get(i).typ);
                    if(i+1!=poleCounter){
                        query.append(", ");
                    }
                    
                }
                query.append(")");

                System.out.println(query);


                try(Connection conn = DriverManager.getConnection(connUrl, "amid", "amid")) {    

                Statement statement = conn.createStatement();

                statement.executeUpdate(query.toString());

                vbox1.getChildren().clear();

                Text tekst = new Text("Tabela została dodana!");
                poleCounter = 0;
                vbox1.getChildren().addAll(tekst);

                TimeUnit.SECONDS.sleep(2);


                vbox1 = dodajTabele();

            } catch (SQLException e) {
                Text blad = new Text("Błąd");
                vbox1.getChildren().addAll(blad);

                try {
                    TimeUnit.SECONDS.sleep(2);

                    vbox1.getChildren().remove(blad);
                } catch (InterruptedException e1) {
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            }
            
        });        

        return vbox1;
    }

    // ! -----------------------------------------------------------

  
    // ! ------------------ Dodawanie rekordu ----------------------

    @SuppressWarnings("unchecked")
    private VBox dodajRekord() {


        Text text = new Text("Dodaj rekord");

        ComboBox comboBox = new ComboBox();
        
        FlowPane combo = new FlowPane(comboBox);
        vbox2 = new VBox(text, combo);
        vbox2.setPadding(new Insets(10, 10, 10, 10));
        vbox2.setMinHeight(300);

        try(Connection conn = DriverManager.getConnection(connUrl, "amid", "amid")) {    

            DatabaseMetaData md = conn.getMetaData();
            String[] types = {"TABLE"};
            ResultSet rs = md.getTables("amid", null, "%", types);
            while (rs.next()) {
                comboBox.getItems().add(rs.getString("TABLE_NAME"));
            }




        } catch (SQLException e) {
            Text blad = new Text("Błąd");
            vbox2.getChildren().addAll(blad);

            try {
                TimeUnit.SECONDS.sleep(2);

                vbox2.getChildren().remove(blad);
            } catch (InterruptedException e1) {
            }
        }

        List<String> dane = new ArrayList<String>();

        FlowPane dodawanieRekordu = new FlowPane();
        dodawanieRekordu.setOrientation(Orientation.VERTICAL);

        comboBox.setOnAction((event) -> {
            dodawanieRekordu.getChildren().clear();
            vbox2.getChildren().removeAll(dodawanieRekordu);
            
            
            Button dodaj = new Button("Dodaj");
            combo.getChildren().clear();
            combo.getChildren().addAll(comboBox);
            combo.getChildren().addAll(dodaj);

            dodaj.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    StringBuilder query = new StringBuilder();

                    query.append("INSERT INTO " + comboBox.getSelectionModel().getSelectedItem().toString() + " VALUES (");

                    for (int i = 0; i<dane.size(); i++) {
                        if(i!=dane.size()-1){
                            query.append("\"" + dane.get(i) + "\", ");
                        } else {
                            query.append("\"" + dane.get(i) + "\")");
                        }
                    }

                    try(Connection conn = DriverManager.getConnection(connUrl, "amid", "amid")) {    

                        Statement statement = conn.createStatement();
        
                        System.out.println(query);
                        statement.executeUpdate(query.toString());

        
                        vbox2.getChildren().clear();
        
                        Text tekst = new Text("Rekord został dodany!");
                        vbox2.getChildren().addAll(tekst);
        
                        TimeUnit.SECONDS.sleep(2);
        
                        vbox2 = dodajTabele();

                    } catch (SQLException e) {
                        Text blad = new Text("Błąd");
                        vbox2.getChildren().addAll(blad);

                        try {
                            TimeUnit.SECONDS.sleep(2);

                            vbox2.getChildren().remove(blad);
                        } catch (InterruptedException e1) {
                        }

                        
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                
            });


            String selectedItem = comboBox.getSelectionModel().getSelectedItem().toString();
        
            try(Connection conn = DriverManager.getConnection(connUrl, "amid", "amid")) {  
                

                Statement statement = conn.createStatement();

                ResultSet rs = statement.executeQuery("SELECT * FROM " + selectedItem + " LIMIT 1");
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnCount = rsmd.getColumnCount();

                for (int i = 1; i <= columnCount; i++ ) {
                  String name = rsmd.getColumnName(i);

                  FlowPane kolumna = new FlowPane();
                  Text nazwaKolumny = new Text(name);
                  TextField wprowadzanie = new TextField();
                  Button zatwierdz = new Button("Zatwierdź");


                  kolumna.getChildren().addAll(nazwaKolumny, wprowadzanie, zatwierdz);
                  dodawanieRekordu.getChildren().addAll(kolumna);

                  zatwierdz.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent arg0) {
                        wprowadzanie.setEditable(false);
                        zatwierdz.setDisable(true);
                        dane.add(wprowadzanie.getText());
                    }
                    
                  });

                }
                vbox2.getChildren().addAll(dodawanieRekordu);





            } catch (SQLException e) {
                System.out.println(e);
            }
        });

        
        return vbox2;
    }

    // ! ------------------------------------------------------------

    // ! ------------------- Wyszukiwanie rekordu -------------------

    @SuppressWarnings("unchecked")
    private VBox wyszukajRekord() {

        Text text = new Text("Wyszukaj rekord");
        FlowPane wynik = new FlowPane();
        wynik.setOrientation(Orientation.VERTICAL);
        vbox3 = new VBox(text);
        vbox3.setPadding(new Insets(10, 10, 10, 10));

        ComboBox comboBox = new ComboBox();
        
        FlowPane combo = new FlowPane(comboBox);

        vbox3.getChildren().addAll(combo);

        try(Connection conn = DriverManager.getConnection(connUrl, "amid", "amid")) {    

            DatabaseMetaData md = conn.getMetaData();
            String[] types = {"TABLE"};
            ResultSet rs = md.getTables("amid", null, "%", types);
            while (rs.next()) {
                comboBox.getItems().add(rs.getString("TABLE_NAME"));
            }



        } catch (SQLException e) {
            System.out.println(e);
        
        }

        ComboBox pole = new ComboBox();

        comboBox.setOnAction((event) -> {

            String selectedItem = comboBox.getSelectionModel().getSelectedItem().toString();

            try(Connection conn = DriverManager.getConnection(connUrl, "amid", "amid")) {  
                
                combo.getChildren().clear();
                vbox3.getChildren().clear();
                combo.getChildren().addAll(comboBox);
                vbox3.getChildren().addAll(combo);

                Statement statement = conn.createStatement();

                ResultSet rs = statement.executeQuery("SELECT * FROM " + selectedItem + " LIMIT 1");
                ResultSetMetaData rsmd = rs.getMetaData();

                columnCount = rsmd.getColumnCount();


                pole.getItems().clear();
                for (int i = 1; i <= columnCount; i++ ) {
                  String name = rsmd.getColumnName(i);

                  pole.getItems().add(name);
                

                }
                
                combo.getChildren().addAll(pole);


            } catch (SQLException e) {
                System.out.println(e);
            }

            pole.setOnAction((e) -> {
               
                

                FlowPane szukanie = new FlowPane();
                Button szukaj = new Button("Szukaj");
                TextField doszukania = new TextField();

                szukanie.getChildren().clear();
                vbox3.getChildren().clear();
                vbox3.getChildren().addAll(combo);

                szukanie.getChildren().addAll(doszukania, szukaj);
                vbox3.getChildren().addAll(szukanie);

                szukaj.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent arg0) {
                        

                        String kolumna = pole.getSelectionModel().getSelectedItem().toString();
                        StringBuilder query = new StringBuilder("SELECT *" + " FROM " + selectedItem + " WHERE " + kolumna + "=");
                        query.append("\"" + doszukania.getText() + "\"");
                        System.out.println(query);

                        try(Connection conn = DriverManager.getConnection(connUrl, "amid", "amid")) {    

                        PreparedStatement s = conn.prepareStatement(query.toString());
                       
                        ResultSet rs = s.executeQuery();
                       
                       wynik.getChildren().clear();
                       vbox3.getChildren().remove(wynik);
                       while(rs.next()) {
                        StringBuilder record = new StringBuilder();
                            for(int i = 1; i<=columnCount; i++){
                                record.append(rs.getString(i) + " ");
                            }
                            Text rekord = new Text(record.toString());
                            wynik.getChildren().add(rekord);
                       }
                       vbox3.getChildren().addAll(wynik);
                       
                        } catch (SQLException e) {
                            System.out.println(e);
                        }
                    }
                    
                });



            });



                
        });

        return vbox3;
    }

    // ! --------------------------------------------------------------------------------

    public void switchPanes(Pane pane){
        borderPane.setCenter(pane);
    }
    

    public static void main(String[] args) {
        launch();
    }

}   