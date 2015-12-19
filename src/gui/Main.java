package gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import vision.VisionModule;

public class Main extends Application {
    private AnchorPane root;
    private Scene scene;
    private ControlsController controlsController;

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/main.fxml"));
            root = loader.load();
            controlsController = loader.getController();
            controlsController.setup();
            scene = new Scene(root, 800, 450);
            scene.getStylesheets().add(getClass().getResource("css/main.css").toExternalForm());
            VisionModule visionModule = new VisionModule();
            visionModule.run();
            primaryStage.setOnCloseRequest((event) -> quit());
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void quit() {
        Platform.exit();
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
