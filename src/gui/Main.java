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
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import vision.ModuleRunner;
import vision.VisionModule;

public class Main extends Application {
    private TabPane root;
    private Scene scene;
    private HashMap<String, ControlsController> tabs = new HashMap<String, ControlsController>();
    private ModuleRunner moduleRunner = new ModuleRunner();
    private HashMap<String, ImageFrame> images = new HashMap<String, ImageFrame>();

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/main.fxml"));
            root = loader.load();
            scene = new Scene(root, 1000, 500);
            scene.getStylesheets().add(getClass().getResource("css/main.css").toExternalForm());
            moduleRunner.run(this);
            primaryStage.setOnCloseRequest((event) -> quit());
            primaryStage.setTitle("Java Vision GUI");
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

    public synchronized void postImage(Mat m, String label, VisionModule requester) {
        // Convert raw image to PNG
        MatOfByte buffer = new MatOfByte();
        Imgcodecs.imencode(".png", m, buffer);
        Image image = new Image(new ByteArrayInputStream(buffer.toArray()));
        // Check if an ImageFrame already exists
        ImageFrame existingFrame = images.get(label);
        if (existingFrame == null) {
            VBox container = new VBox();
            Text text = new Text(label);
            ImageView imageView = new ImageView(image);
            container.setAlignment(Pos.CENTER);
            container.getChildren().addAll(imageView, text);
            images.put(label, new ImageFrame(container, imageView, text));
            // Check if the VisionModule already has its own tab
            if (tabs.get(requester.getName()) != null) {
                Platform.runLater(() -> {
                    tabs.get(requester.getName()).flowPane.getChildren().add(container);
                });
            }
            else {
                // Create a new tab for the VisionModule
                FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/module_main.fxml"));
                SplitPane moduleContainer = null;
                try {
                    moduleContainer = loader.load();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ControlsController controlsController = loader.getController();
                controlsController.setup();
                controlsController.flowPane.getChildren().add(container);
                tabs.put(requester.getName(), controlsController);
                final SplitPane finalModuleContainer = moduleContainer;
                Platform.runLater(() -> {
                    root.getTabs().add(new Tab(requester.getName(), finalModuleContainer));
                });
            }
        }
        else {
            // Update the existing ImageFrame
            Platform.runLater(() -> {
                existingFrame.imageView.setImage(image);
                existingFrame.imageView.toFront();
                existingFrame.label.toFront();
            });
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    private class ImageFrame {
        private VBox container;
        private ImageView imageView;
        private Text label;
        public ImageFrame(VBox container, ImageView imageView, Text label) {
            this.container = container;
            this.imageView = imageView;
            this.label = label;
        }
    }
}
