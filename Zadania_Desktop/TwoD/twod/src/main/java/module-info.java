module com.twod {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.twod to javafx.fxml;
    exports com.twod;
}
