module com.compinter {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.media;
    requires mail;
    requires java.logging;

    
    opens com.compinter to javafx.fxml;
    opens com.compinter.controller to javafx.fxml;
    exports com.compinter;

}
