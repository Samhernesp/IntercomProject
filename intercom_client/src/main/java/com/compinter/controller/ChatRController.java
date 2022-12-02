package com.compinter.controller;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;

import com.compinter.App;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.media.AudioClip;

public class ChatRController {
    private App main;
    private AudioClip clip;

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
    }

    //Contiene el sonido y el volumen de reproducción.
    public void start() {
        playSound("ringtone.mp3", 100);
    }

    //Acepta que iniciar un chat con el apartamento anunciado
    @FXML
    void startChat() throws IOException {
        Platform.runLater(() -> {
            try {
                App.getClient().acceptChat(main.getNumber());
            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
        clip.stop();
        main.openChat();
    }

    //Niega que se abra un chat con el apartamento anunciado
    @FXML
    void denyChat() throws IOException {
        Platform.runLater(() -> App.getClient().denyChat(main.getNumber()));
        clip.stop();
        main.openHome();
    }

    public void setMain(App app) {
        this.main = app;
    }
}