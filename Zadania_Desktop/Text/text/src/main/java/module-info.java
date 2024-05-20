module com.text {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.text to javafx.fxml;
    exports com.text;
}
