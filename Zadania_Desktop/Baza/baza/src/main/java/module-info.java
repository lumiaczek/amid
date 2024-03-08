module com.baza {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.baza to javafx.fxml;
    exports com.baza;
}
