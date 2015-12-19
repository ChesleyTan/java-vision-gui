package gui;

import java.io.ByteArrayInputStream;
import java.util.HashMap;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import vision.ModuleRunner;

public class Main extends Application {
    private AnchorPane root;
    private Scene scene;
    private ControlsController controlsController;
    private ModuleRunner moduleRunner = new ModuleRunner();
    private HashMap<String, ImageFrame> images = new HashMap<String, ImageFrame>();

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/main.fxml"));
            root = loader.load();
            controlsController = loader.getController();
            controlsController.setup();
            scene = new Scene(root, 800, 450);
            scene.getStylesheets().add(getClass().getResource("css/main.css").toExternalForm());
            moduleRunner.run(this);
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

    public void postImage(Mat m, String label) {
        // Convert raw image to PNG
        MatOfByte buffer = new MatOfByte();
        Imgcodecs.imencode(".png", m, buffer);
        Image image = new Image(new ByteArrayInputStream(buffer.toArray()));
        if (images.get(label) == null) {
            VBox container = new VBox();
            Text text = new Text(label);
            ImageView imageView = new ImageView(image);
            container.setAlignment(Pos.CENTER);
            container.getChildren().addAll(imageView, text);
            images.put(label, new ImageFrame(container, imageView));
            Platform.runLater(() -> {
                root.getChildren().add(container);
            });
        }
        else {
            images.get(label).imageView.setImage(image);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    private class ImageFrame {
        private VBox container;
        private ImageView imageView;
        public ImageFrame(VBox container, ImageView imageView) {
            this.container = container;
            this.imageView = imageView;
        }
    }
}
