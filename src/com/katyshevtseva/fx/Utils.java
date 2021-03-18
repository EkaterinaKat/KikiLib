package com.katyshevtseva.fx;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;

public class Utils {

    private Utils() {
    }

    public static void closeWindowThatContains(Node node) {
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }

    public static void disableNonNumericChars(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    public static void setImageOnButton(String imageName, Button button, int size) {
        setImageOnButton(imageName, button, size, size);
    }

    public static void setImageOnButton(String imageName, Button button, int height, int width) {
        Image image = new Image(imageName);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);
        button.graphicProperty().setValue(imageView);
    }

    public static void associateButtonWithControls(Button button, Control... controls) {
        associateButtonWithControls(button, Arrays.asList(controls));
    }

    public static void associateButtonWithControls(Button button, List<Control> controls) {
        button.setDisable(true);
        for (Control control : controls) {
            if (control instanceof TextField) {
                TextField textField = (TextField) control;
                textField.textProperty().addListener(observable -> setButtonAccessibility(button, controls));
            } else if (control instanceof TextArea) {
                TextArea textArea = (TextArea) control;
                textArea.textProperty().addListener(observable -> setButtonAccessibility(button, controls));
            } else if (control instanceof ComboBox) {
                ComboBox comboBox = (ComboBox) control;
                comboBox.valueProperty().addListener((observable -> setButtonAccessibility(button, controls)));
            } else if (control instanceof DatePicker) {
                DatePicker datePicker = (DatePicker) control;
                datePicker.valueProperty().addListener(observable -> setButtonAccessibility(button, controls));
            } else
                throw new RuntimeException("Элемент неизвестного типа");
        }
    }

    private static void setButtonAccessibility(Button button, List<Control> controls) {
        boolean disableButton = false;
        for (Control control : controls) {
            if (control instanceof TextField && ((TextField) control).getText().trim().equals("")) {
                disableButton = true;
            } else if (control instanceof TextArea && ((TextArea) control).getText().trim().equals("")) {
                disableButton = true;
            } else if (control instanceof ComboBox && ((ComboBox) control).getValue() == null) {
                disableButton = true;
            } else if (control instanceof DatePicker && ((DatePicker) control).getValue() == null) {
                disableButton = true;
            }
            button.setDisable(disableButton);
        }
    }

    public static Pane getPaneWithHeight(int height) {
        Pane pane = new Pane();
        pane.setMinHeight(height);
        pane.setMaxHeight(height);
        return pane;
    }

    public static Pane getPaneWithWidth(int width) {
        Pane pane = new Pane();
        pane.setMinWidth(width);
        pane.setMaxWidth(width);
        return pane;
    }
}
