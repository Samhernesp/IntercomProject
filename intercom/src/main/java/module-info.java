module com.compinter {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.media;

    opens com.compinter to javafx.fxml;
    opens com.compinter.controller to javafx.fxml;

    exports com.compinter;
    exports com.compinter.controller;
    exports com.compinter.net;
}