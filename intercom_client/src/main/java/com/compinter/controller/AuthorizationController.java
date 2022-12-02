package com.compinter.controller;

import java.io.IOException;

import com.compinter.App;
import com.compinter.net.Client;
import com.compinter.services.MailServices;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;

public class AuthorizationController {
    private MailServices mail = new MailServices();

    @FXML
    private Button panicButton;
    
    @FXML
    private Label apartmentNumber;

    @FXML
    private Button startConversationButton;

	private App main;

    private Client client;

    //Reproduce un sonido de alarma al presionar el botón de pánico.
    //Llama al método de crear y enviar Email al contacto de enmergencia.
    @FXML
    void panicButtonAction(ActionEvent event) {
        client.playAlarm(main.getNumber());
        mail.createAndSendEmail(client.getEmergencyEmail(), main.getNumber());
    }

    //Inicia una conversacion con el otro apartamento.
    @FXML
    void startConversationButtonAction() throws IOException {
       client.requestChat(main.getNumber());
    }

	public void setMain(App main) {
		this.main = main;
	}

    //Muestra alerta de que el Email ha sido enviado.
    public void showConfirmation(){
        Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText(null);
		alert.setContentText("The email was sended");
		alert.showAndWait();
    }
    
    public void setClient(Client client) {
        this.client = client;
    }

    public void openCall(String name) throws IOException {
        main.openCall(name);
    }

    // Método que establece el número del apartamento. 
    public void setNumber(int number) {
        apartmentNumber.setText(apartmentNumber.getText() + number);
        main.setNumber(number);
    }

	public void openChatR() throws IOException {
        main.openChatR();
	}

    public void deniedChat() {
        Alert alert = new Alert(AlertType.WARNING);
		alert.setHeaderText(null);
		alert.setContentText("The chat was rejected");
		alert.showAndWait();
    }

    public void acceptedChat() throws IOException {
        main.openChat();
    }

    public void finishChat() throws IOException {
        main.openHome();
        Alert alert = new Alert(AlertType.WARNING);
		alert.setHeaderText(null);
		alert.setContentText("The chat was closed by the other apartment");
		alert.showAndWait();
    }
}
