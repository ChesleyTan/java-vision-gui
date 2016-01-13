package gui;

import java.util.HashMap;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ImageViewer {

    private static ImageViewer instance;
    private HashMap<ImageView, Stage> windows = new HashMap<>();

    public static ImageViewer getInstance() {
        if (instance == null) {
            instance = new ImageViewer();
        }
        return instance;
    }

    public void showImage(String label, ImageView image) {
        Stage imageWindow = windows.get(image);
        if (imageWindow != null) {
            imageWindow.toFront();
        }
        else {
            imageWindow = new Stage();
            double imageWidth = image.getImage().getWidth();
            double imageHeight = image.getImage().getHeight();
            double resizeRatio = (double) 960 / Math.max(imageWidth, imageHeight);
            ImageView resizedView = new ImageView(image.getImage());
            // blow up image size to max of 960px
            resizedView.setFitWidth(imageWidth * resizeRatio);
            resizedView.setFitHeight(imageHeight * resizeRatio);
            resizedView.imageProperty().bind(image.imageProperty());
            imageWindow.setTitle(label);
            imageWindow.setWidth(imageWidth * resizeRatio);
            imageWindow.setHeight(imageHeight * resizeRatio);
            imageWindow.setScene(new Scene(new Pane(resizedView)));
            imageWindow.setOnCloseRequest((event) -> {
                windows.remove(image);
            });
            final Stage finalImageWindow = imageWindow;
            Platform.runLater(() -> {
                finalImageWindow.show();
            });
            windows.put(image, imageWindow);
        }
    }

}
