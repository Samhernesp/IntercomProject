package com.compinter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

import com.compinter.controller.AreaController;
import com.compinter.controller.PorterLodgeController;
import com.compinter.net.Server;

/**
 * JavaFX App
 */
public class App extends Application {
    private static Scene scene;
    private Stage currentStage;
    private PorterLodgeController porterLodgeController;
	static private Server core;
    
    @Override
    public void start(Stage stage) throws IOException {
        
        FXMLLoader loader2 = getLoader("porterLodge");
        loader2.load();
        porterLodgeController = loader2.getController();
        
        core = new Server(porterLodgeController);
        System.out.println(core +" CORE");

		FXMLLoader loader = getLoader("area");
        scene = new Scene(loader.load(), 600, 400);
		AreaController controller = loader.getController();
        core.setController(controller);

        controller.setMain(this);
		controller.setServer(core);
        
        stage.setScene(scene);
        currentStage = stage;
        currentStage.getIcons().add(new Image(App.class.getResource("appIcon.png").toString()));
        currentStage.setTitle("Citófono Porteria");

        currentStage.show();
        core.start();
    }

    /*static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }*/

    private static FXMLLoader getLoader(String fxml) throws IOException {
        return new FXMLLoader(App.class.getResource(fxml + ".fxml"));
    }

    public static void main(String[] args) {
        launch();
    }

    public void openPorterLodge() throws IOException {
        FXMLLoader loader = getLoader("porterLodge");
        scene = new Scene(loader.load(), 600, 400);
		porterLodgeController.setMain(this);
        currentStage.setScene(scene);
    }

    public static Server getServer() {
        return core;
    }
}