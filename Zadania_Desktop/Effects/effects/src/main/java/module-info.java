module com.effects {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.effects to javafx.fxml;
    exports com.effects;
}
