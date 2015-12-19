package gui;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class ControlsController {
    @FXML
    TextArea textArea;

    @FXML
    public void setup() {
        textArea.setText("Hello world!");
    }
}
