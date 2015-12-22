package gui;

import java.lang.reflect.Field;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import util.DebugPrinter;
import vision.DoubleSliderVariable;
import vision.IntegerSliderVariable;
import vision.SliderVariable;
import vision.VisionModule;

public class ControlsController {
    @FXML
    FlowPane flowPane;
    @FXML
    VBox controlsContainer;

    @FXML
    public void setup(VisionModule module) {
        ArrayList<SliderVariableWrapper> sliders = new ArrayList<SliderVariableWrapper>();
        Button restoreDefaultsButton = new Button("Restore defaults");
        restoreDefaultsButton.setPrefWidth(controlsContainer.getPrefWidth());
        restoreDefaultsButton.setAlignment(Pos.CENTER);
        restoreDefaultsButton.setOnAction((event) -> {
            for (SliderVariableWrapper slider : sliders) {
                if (slider.sliderVariable instanceof IntegerSliderVariable) {
                    IntegerSliderVariable isv = (IntegerSliderVariable) slider.sliderVariable;
                    slider.slider.setValue(isv.DEFAULT);
                }
                if (slider.sliderVariable instanceof DoubleSliderVariable) {
                    DoubleSliderVariable dsv = (DoubleSliderVariable) slider.sliderVariable;
                    slider.slider.setValue(dsv.DEFAULT);
                }
            }
        });
        Platform.runLater(() -> {
            controlsContainer.getChildren().add(restoreDefaultsButton);
        });
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
                label.getStyleClass().add("slider-label");
                final Text value = new Text(Integer.toString(isv.DEFAULT));
                value.getStyleClass().add("slider-value");
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
                sliders.add(new SliderVariableWrapper(slider, finalIsv));
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
                label.getStyleClass().add("slider-label");
                final DecimalFormat formatter = new DecimalFormat("#.######",
                        DecimalFormatSymbols.getInstance(Locale.ENGLISH));
                formatter.setRoundingMode(RoundingMode.DOWN);
                final Text value = new Text(formatter.format(dsv.DEFAULT));
                value.getStyleClass().add("slider-value");
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
                sliders.add(new SliderVariableWrapper(slider, finalDsv));
                Platform.runLater(() -> {
                    controlsContainer.getChildren().add(sliderContainer);
                });
            }
        }
    }

    private class SliderVariableWrapper {
        private Slider slider;
        private SliderVariable sliderVariable;
        private SliderVariableWrapper(Slider slider, SliderVariable sliderVariable) {
            this.slider = slider;
            this.sliderVariable = sliderVariable;
        }
    }
}
