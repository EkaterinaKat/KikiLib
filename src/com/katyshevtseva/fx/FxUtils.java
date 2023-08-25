package com.katyshevtseva.fx;

import com.katyshevtseva.date.Period;
import com.katyshevtseva.fx.FxImageCreationUtil.IconPicture;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class FxUtils {

    private FxUtils() {
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

    public static void setImageOnButton(IconPicture iconPicture, Button button, int size) {
        setImageOnButton(iconPicture, button, size, size);
    }

    public static void setImageOnButton(IconPicture iconPicture, Button button, int height, int width) {
        ImageView imageView = new ImageView(FxImageCreationUtil.getIcon(iconPicture));
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
        setButtonAccessibility(button, controls);
    }

    private static void setButtonAccessibility(Button button, List<Control> controls) {
        boolean disableButton = false;
        for (Control control : controls) {
            if (control instanceof TextField && textFieldIsEmpty((TextField) control)) {
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

    private static boolean textFieldIsEmpty(TextField textField) {
        return textField.getText() == null || textField.getText().trim().equals("");
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

    public static <E> void setComboBoxItems(ComboBox<E> comboBox, E[] items) {
        ObservableList<E> observableList = FXCollections.observableArrayList(items);
        comboBox.setItems(observableList);
    }

    public static <E> void setComboBoxItems(ComboBox<E> comboBox, List<E> items) {
        ObservableList<E> observableList = FXCollections.observableArrayList(items);
        comboBox.setItems(observableList);
    }

    public static <E> void setComboBoxItemsAndSetSelectedFirstItem(ComboBox<E> comboBox, List<E> items) {
        ObservableList<E> observableList = FXCollections.observableArrayList(items);
        comboBox.setItems(observableList);
        if (items.size() > 0)
            comboBox.setValue(items.get(0));
    }

    public static <E> void setComboBoxItems(ComboBox<E> comboBox, List<E> items, E defaultSelectedItem) {
        ObservableList<E> observableList = FXCollections.observableArrayList(items);
        comboBox.setItems(observableList);
        comboBox.setValue(defaultSelectedItem);
    }

    public static <E> void setComboBoxItems(ComboBox<E> comboBox, E[] items, E defaultSelectedItem) {
        ObservableList<E> observableList = FXCollections.observableArrayList(items);
        comboBox.setItems(observableList);
        comboBox.setValue(defaultSelectedItem);
    }

    public static Period getPeriod(DatePicker startDatePicker, DatePicker endDatePicker) {
        return new Period(getDate(startDatePicker), getDate(endDatePicker));
    }

    public static Date getDate(DatePicker datePicker) {
        return datePicker.getValue() != null ? java.sql.Date.valueOf(datePicker.getValue()) : null;
    }

    public static void setSize(Region region, Size size) {
        setWidth(region, size.getWidth());
        setHeight(region, size.getHeight());
    }

    public static void setSize(Region region, int size) {
        setWidth(region, size);
        setHeight(region, size);
    }

    public static void setWidth(Region region, int width) {
        region.setMaxWidth(width);
        region.setMinWidth(width);
    }

    public static void setHeight(Region region, int height) {
        region.setMaxHeight(height);
        region.setMinHeight(height);
    }

    public static void setDate(DatePicker datePicker, Date date) {
        datePicker.setValue(new java.sql.Date(date.getTime()).toLocalDate());
    }

    public static void setCurrentDate(DatePicker datePicker) {
        datePicker.setValue(LocalDate.now());
    }

    public static Label getLabel(String text, int width) {
        Label label = new Label();
        tuneLabel(label, text, null, width);
        return label;
    }

    public static Label getLabel(String text, Integer textSize, Integer width) {
        Label label = new Label();
        tuneLabel(label, text, textSize, width);
        return label;
    }

    public static void tuneLabel(Label label, String text, Integer textSize, Integer width) {
        if (width != null) {
            label.setMaxWidth(width);
        }
        label.setWrapText(true);
        label.setText(text);
        if (textSize != null) {
            label.setStyle(Styler.getTextSizeStyle(textSize));
        }

    }

    public static Node frame(Node node, int frameWidth) {
        return frame(node, frameWidth, frameWidth);
    }

    public static Node frame(Node node, int vertical, int horizontal) {
        VBox vBox = new VBox();
        vBox.getChildren().addAll(getPaneWithHeight(vertical), node, getPaneWithHeight(vertical));
        HBox hBox = new HBox();
        hBox.getChildren().addAll(getPaneWithWidth(horizontal), node, getPaneWithWidth(horizontal));
        return hBox;
    }
}
