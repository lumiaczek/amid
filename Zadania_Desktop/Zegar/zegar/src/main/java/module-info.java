module com.zegar {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.zegar to javafx.fxml;
    exports com.zegar;
}
