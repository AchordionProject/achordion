module com.github.achordion.client {
    requires javafx.controls;
    requires javafx.fxml;
    //added Java.desktop because it is required for javax.sound.sampled
    requires java.desktop;

    opens com.github.achordion.client to javafx.fxml;
    exports com.github.achordion.client;
}