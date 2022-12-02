package com.compinter.controller;

import java.io.File;
import java.io.IOException;

import com.compinter.App;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.media.AudioClip;

public class CallController {
    private App main;
    private AudioClip clip;
    private boolean flag;

    @FXML
    private Button admitButton;

    @FXML
    private Button denyButton;

    @FXML
    private Label nameVisitorLabel1;

    @FXML
    private Label visitorName;

    //Reproduce un sonido de llamada.
    public void playSound(String file, float volume) {
        try {
            clip = new AudioClip(new File("src/main/resources/com/compinter/" + file).toURI().toString());
            clip.setVolume(volume);
            clip.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

    public void setup(App main, String name) {
        this.main = main;
        visitorName.setText(name);
    }

    public void start() {
        playSound("ringtone.mp3", 100);
    }

    //Acepta que ingrese el visitante anunciado, detiene la reproduccion del sonido de llamada y vuelve a la pantalla principal.
    @FXML
    void admitButtonAction() throws IOException {
        Platform.runLater(() -> App.getClient().acceptVisitor(main.getNumber()));
        flag = false;
        clip.stop();
        main.openHome();
    }
    //Niega que ingrese el visitante anunciado, detiene la reproduccion del sonido de llamada y vuelve a la pantalla principal.
    @FXML
    void denyButtonAction() throws IOException {
        Platform.runLater(() -> App.getClient().denyVisitor(main.getNumber()));
        flag = false;
        clip.stop();
        main.openHome();
    }
}