module com.stoper {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.stoper to javafx.fxml;
    exports com.stoper;
}
