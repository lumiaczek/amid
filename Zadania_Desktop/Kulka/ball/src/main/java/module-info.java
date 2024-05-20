module com.ball {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.ball to javafx.fxml;
    exports com.ball;
}
