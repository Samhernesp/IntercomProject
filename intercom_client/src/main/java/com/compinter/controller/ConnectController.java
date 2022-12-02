package com.compinter.controller;

import java.io.IOException;

import com.compinter.App;
import com.compinter.net.Client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class ConnectController {
    private App main;

    @FXML
    private Button connectButton;

    @FXML
    private TextField ipAddress;

    @FXML
    private TextField emergencyEmail;

    //Cuando en la interfaz se selecciona continuar y los campos han sido llenados correctamente
    //Se tiene el número del apartamento y el Email de enmergencia.
    //En caso de ser todo correcto la coneccion es exitosa.
    //Y se abre una nueva interfaz para el apartamento.
    @FXML
    void connectAction(ActionEvent event) throws IOException {
        if (ipAddress.getText().equals("") || emergencyEmail.equals("") || !emergencyEmail.getText().contains("@") || !emergencyEmail.getText().contains(".")){
            Alert alert = new Alert(AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("There are invalid fields");
            alert.showAndWait();
        } else {
            try{
                Client client = new Client(ipAddress.getText(), emergencyEmail.getText());
                main.setClient(client);
                main.openHome();

                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Connection established succesfully");
                alert.showAndWait();
            } catch (IOException e) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Something went bad, please check ip address and internet conenctivity");
                alert.showAndWait();
            }
            
        }
    }

    public void setMain(App main) {
        this.main = main;
    }
}
