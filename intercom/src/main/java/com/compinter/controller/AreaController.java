package com.compinter.controller;

import java.io.IOException;

import com.compinter.App;
import com.compinter.net.Server;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class AreaController {
    private App main;
    private Server server;

    public void setMain(App main) {
        this.main = main;
    }

    @FXML
    private Button continueButton;

    @FXML
    private TextArea log;

    //Despues de verificar que la conexion es correcta se habilita el boton de continuar, el cual desencadena este método
    //para pasar a la pantalla principal de la porteria.

    @FXML
    void continueAction(ActionEvent event) throws IOException {
        main.openPorterLodge();
    }

    public void setServer(Server core) {
        this.server = core;
    }

    public void println(String text) {
        Platform.runLater(() -> {
            log.appendText(text);
            log.appendText("\n");
        });
    }

    public void print(String text) {
        Platform.runLater(() -> {
            log.appendText(text); 
        });
    }

    public void enableContinue() {
        continueButton.setDisable(false);
    }
}
