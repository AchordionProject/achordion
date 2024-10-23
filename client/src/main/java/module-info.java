module com.github.achordion.client {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.github.achordion.client to javafx.fxml;
    exports com.github.achordion.client;
}