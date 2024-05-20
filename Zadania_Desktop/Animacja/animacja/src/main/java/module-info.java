module com.animacja {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.animacja to javafx.fxml;
    exports com.animacja;
}
