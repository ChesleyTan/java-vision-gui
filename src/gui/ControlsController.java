package gui;

import java.lang.reflect.Field;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Slider;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import util.DebugPrinter;
import vision.DoubleSliderVariable;
import vision.IntegerSliderVariable;
import vision.VisionModule;

public class ControlsController {
    @FXML
    FlowPane flowPane;
    @FXML
    VBox controlsContainer;

    @FXML
    public void setup(VisionModule module) {
        for (Field f : module.getClass().getFields()) {
            Class<?> fType = f.getType();
            if (fType.isAssignableFrom(IntegerSliderVariable.class)) {
                DebugPrinter.println("Found IntegerSliderVariable: " + f.getName());
                IntegerSliderVariable isv = null;
                try {
                    isv = (IntegerSliderVariable) f.get(module);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                VBox sliderContainer = new VBox();
                sliderContainer.setAlignment(Pos.CENTER);
                Slider slider = new Slider(isv.MIN, isv.MAX, isv.DEFAULT);
                Text label = new Text(isv.LABEL);
                final Text value = new Text(Integer.toString(isv.DEFAULT));
                final IntegerSliderVariable finalIsv = isv;
                sliderContainer.getChildren().addAll(slider, value, label);
                slider.valueProperty().addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable,
                            Number oldValue, Number newValue) {
                        int intValue = newValue.intValue();
                        finalIsv.set(intValue);
                        value.setText(Integer.toString(intValue));
                    }
                });
                Platform.runLater(() -> {
                    controlsContainer.getChildren().add(sliderContainer);
                });
            }
            else if (fType.isAssignableFrom(DoubleSliderVariable.class)) {
                DebugPrinter.println("Found DoubleSliderVariable: " + f.getName());
                DoubleSliderVariable dsv = null;
                try {
                    dsv = (DoubleSliderVariable) f.get(module);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                VBox sliderContainer = new VBox();
                sliderContainer.setAlignment(Pos.CENTER);
                Slider slider = new Slider(dsv.MIN, dsv.MAX, dsv.DEFAULT);
                Text label = new Text(dsv.LABEL);
                final DecimalFormat formatter = new DecimalFormat("#.######",
                        DecimalFormatSymbols.getInstance(Locale.ENGLISH));
                formatter.setRoundingMode(RoundingMode.DOWN);
                final Text value = new Text(formatter.format(dsv.DEFAULT));
                final DoubleSliderVariable finalDsv = dsv;
                sliderContainer.getChildren().addAll(slider, value, label);
                slider.valueProperty().addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable,
                            Number oldValue, Number newValue) {
                        double doubleValue = newValue.doubleValue();
                        finalDsv.set(doubleValue);
                        value.setText(formatter.format(doubleValue));
                    }
                });
                Platform.runLater(() -> {
                    controlsContainer.getChildren().add(sliderContainer);
                });
            }
        }
    }
}
