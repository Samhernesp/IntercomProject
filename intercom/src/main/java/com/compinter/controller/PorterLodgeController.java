package com.compinter.controller;

import javafx.fxml.FXML;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import java.io.File;
import java.util.ArrayList;

import com.compinter.App;

public class PorterLodgeController {
    private App main;
    private Media media = new Media(new File("src/main/resources/com/compinter/alarmTone.mp3").toURI().toString());  
    MediaPlayer mediaPlayer = new MediaPlayer(media);  

    private int maxApartments = 2;

    @FXML
    private Button announceButton;

    @FXML
    private ComboBox<String> apartmentSelector;

    @FXML
    private TextField visitorName;


    @FXML
    void initialize() {
        ArrayList<String> arr = new ArrayList<>();

        for (int i=1; i<=maxApartments; i++) {
            arr.add(i + "");
        }

        ObservableList<String> values = FXCollections.observableArrayList(arr);
        apartmentSelector.setItems(values);
    }
    
    @FXML
    void stopAlarm() {
        mediaPlayer.setVolume(0);
    }

    // Verifica que se rellena correctamente los espacios que requieren el nombre del visitante y el número de 
    //apartamento, despues llama al método announce que es el encargado de notificar al apartamento de una visita.
    @FXML
    void announceAction() {
        String apartmentNumber = apartmentSelector.getSelectionModel().getSelectedItem();
        String visitor= visitorName.getText();

        if (visitor.equals("") || visitor == null){
            Alert alert = new Alert(AlertType.WARNING);
		    alert.setHeaderText(null);
		    alert.setContentText("You have to write a name");
		    alert.showAndWait();
        } else if (apartmentNumber == null) {
            Alert alert = new Alert(AlertType.WARNING);
		    alert.setHeaderText(null);
		    alert.setContentText("You have to select an apartment number");
		    alert.showAndWait();
        } else {
            Platform.runLater(()->{
                boolean result = App.getServer().announce(Integer.parseInt(apartmentNumber), visitor);
                if (result) {
                    visitorName.setText("");
                    apartmentSelector.setValue(null);
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Request sended");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setHeaderText(null);
                    alert.setContentText("There is already an open request for that apartment");
                    alert.showAndWait();
                }
            });
        }
    }

	public void setMain(App main) {
		this.main = main;
	}

    // Método que reproduce el sonido de alerta cuando, en el apartamento es presionado el botón de panico.
    public void playSound(String file, float volume) {
        mediaPlayer.setVolume(100);
        mediaPlayer.play();
	}

    // Despues de que es anunciado el visitante, el dueño decide si aceptar o rechazar la visita.
    // este método muestra una alerta diciendo que se ha aceptado en el apartamento.
    public void showPermissionAccepted(int number){
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("The visitor was accepted in apartment #" + number);
            alert.showAndWait();
        });
    }


    // Despues de que es anunciado el visitante, el dueño decide si aceptar o rechazar la visita.
    // este método muestra una alerta diciendo que no se ha aceptado en el apartamento.

	public void showPermissionDeny(int number) {
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("The visitor was not accepted in apartment #" + number);
            alert.showAndWait();
        });
	}

    // Hace el llamado al método que reproduce el sonido de alerta, es el encargado de asignar el nombre del archivo y el volumen.
    public void panicButton(int number) {
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Panic button was actioned in apartment #" + number);
            alert.showAndWait();
        });
        playSound("alarmTone.mp3", 100);
    }
}