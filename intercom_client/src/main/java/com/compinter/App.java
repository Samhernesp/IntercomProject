package com.compinter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import com.compinter.controller.AuthorizationController;
import com.compinter.controller.CallController;
import com.compinter.controller.ChatController;
import com.compinter.controller.ChatRController;
import com.compinter.controller.ConnectController;
import com.compinter.net.Client;

/**
 * JavaFX App
 */
public class App extends Application {
    private static Client core;

    private static Scene scene;
    private Stage currentStage;
    private int number;

    //Abre la pantalla principal de la conexión entre cliente y servidor.
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = getLoader("connect");
        scene = new Scene(loader.load(), 600, 400);
		ConnectController controller = loader.getController();
        controller.setMain(this);
        stage.setScene(scene);
        currentStage = stage;
        currentStage.setTitle("Citófono Apartamento");
        currentStage.getIcons().add(new Image(App.class.getResource("appIcon.png").toString()));

        currentStage.show();
    }

    private static FXMLLoader getLoader(String fxml) throws IOException {
        return new FXMLLoader(App.class.getResource(fxml + ".fxml"));
    }

    public static void main(String[] args) {
        launch();
    }

    //Abre la interaz de llamada de un visitante, pasando como parámetro el nombre. Con el controlador correspondiente.
    public void openCall(String name) throws IOException {
        FXMLLoader loader = getLoader("call");
        scene = new Scene(loader.load(), 600, 400);
		CallController controller = loader.getController();

        controller.setup(this, name);
        controller.start();
        currentStage.setScene(scene);
    }

    //Abre la interfaz de los apartamentos. Con el controlador correspondiente.
    public void openHome() throws IOException {
        FXMLLoader loader = getLoader("authorization");
        scene = new Scene(loader.load(), 600, 400);
        AuthorizationController controller = loader.getController();
        controller.setMain(this);
        controller.setNumber(number);
        controller.setClient(core);
        core.setController(controller);
        core.start();

        currentStage.setScene(scene);
    }

    public static Client getClient() {
        return core;
    }

    //Asigna el nombre de la persona del apartamento.
    public void setClient(Client client) throws IOException {
        core = client;
    }

    //Asigna el número del apartamento
    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void openChatR() throws IOException {
        FXMLLoader loader = getLoader("chatR");
        scene = new Scene(loader.load(), 600, 400);
		ChatRController controller = loader.getController();
        controller.setMain(this);
        controller.start();

        currentStage.setScene(scene);
    }

    public void openChat() throws IOException {
        FXMLLoader loader = getLoader("chat");
        scene = new Scene(loader.load(), 600, 400);
		ChatController controller = loader.getController();
        controller.setMain(this);
        core.setController(controller);
        controller.setClient(core);

        currentStage.setScene(scene);
    }
}