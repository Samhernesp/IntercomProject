package com.compinter.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.io.IOException;

import com.compinter.App;
import com.compinter.net.Client;

import javafx.event.ActionEvent;

public class ChatController {
    private App main;
    private Client client;

    @FXML
    private Button backButton;

    @FXML
    private TextArea logInput;

    @FXML
    private TextArea logOutput;

    @FXML
    private TextArea messageInput;

    @FXML
    void backButtonAction(ActionEvent event) throws IOException {
        client.finishChat(main.getNumber());
        main.openHome();
    }

    public void printInput(String text) {
        logInput.appendText(text);
        logInput.appendText("\n");
        logOutput.appendText("\n");
    }

    public void printOutput(String text) {
        logOutput.appendText(text);
        logOutput.appendText("\n");
        logInput.appendText("\n");
    }

    // Cuando el boton de enviar mensaje es oprimido desencadena este método que se encarga de verificar que la cadena no se encuentre
    // vacia, imprima en pantalla el mensaje y llama al método sendMessage para enviar el mensaje correspondiente.
    @FXML
    void sendMessage() {
        String msg = messageInput.getText();
        if (!msg.equals("")) {
            printOutput(msg);
            client.sendMessage(msg, main.getNumber());
        }
        messageInput.setText("");
    }

    public void receiveMessage(String msg) {
        printInput(msg);
    }

    public void setMain(App app) {
        this.main = app;
    }

    public void setClient(Client core) {
        this.client = core;
    }
}
