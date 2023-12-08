module com.formularz {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.formularz to javafx.fxml;
    exports com.formularz;
}
