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
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import modules.VisionModuleSuite;
import vision.ModuleRunner;
import vision.VisionModule;

public class Main extends Application {
    private TabPane root;
    private Scene scene;
    private HashMap<Integer, ControlsController> tabs = new HashMap<Integer, ControlsController>();
    private ModuleRunner moduleRunner = new ModuleRunner();
    private HashMap<String, ImageFrame> images = new HashMap<String, ImageFrame>();

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/main.fxml"));
            root = loader.load();
            scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("css/main.css").toString());
            // Initialize ModuleRunner with VisionModuleSuite
            new VisionModuleSuite();
            for (VisionModule module : moduleRunner.getModules()) {
                FXMLLoader tabLoader = new FXMLLoader(getClass().getResource("fxml/module_main.fxml"));
                final SplitPane moduleContainer = tabLoader.load();
                ControlsController controlsController = tabLoader.getController();
                controlsController.setup(module);
                tabs.put(module.hashCode(), controlsController);
                root.getTabs().add(new Tab(module.getName(), moduleContainer));
            }
            moduleRunner.run(this);
            primaryStage.setOnCloseRequest((event) -> quit());
            primaryStage.setTitle("Java Vision GUI");
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void quit() {
        Platform.exit();
        System.exit(0);
    }

    public synchronized void postImage(Mat m, String label, VisionModule requester) {
        String key = requester.hashCode() + label;
        // Convert raw image to PNG
        MatOfByte buffer = new MatOfByte();
        Imgcodecs.imencode(".png", m, buffer);
        Image image = new Image(new ByteArrayInputStream(buffer.toArray()));
        // Check if an ImageFrame already exists
        ImageFrame existingFrame = images.get(key);
        if (existingFrame == null) {
            VBox container = new VBox();
            container.setAlignment(Pos.CENTER);
            ImageView imageView = new ImageView(image);
            Text text = new Text(label);
            text.getStyleClass().add("image-label");
            container.getChildren().addAll(imageView, text);
            images.put(key, new ImageFrame(imageView, text));
            Platform.runLater(() -> {
                tabs.get(requester.hashCode()).flowPane.getChildren().add(container);
            });
            container.setOnMouseClicked((event) -> {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    if (event.getClickCount() == 2) {
                        ImageViewer.getInstance().showImage(label, imageView);
                    }
                }
            });
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
        private ImageView imageView;
        private Text label;

        public ImageFrame(ImageView imageView, Text label) {
            this.imageView = imageView;
            this.label = label;
        }
    }
}
