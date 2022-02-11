package com.katyshevtseva.fx;

import com.katyshevtseva.general.OneArgKnob;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.util.Callback;

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

    public static <T> void adjustButtonColumn(TableColumn<T, Void> column, String buttonText, OneArgKnob<T> buttonClickHandler) {
        adjustButtonColumn(column, buttonText, buttonClickHandler, null);
    }

    public static <T> void adjustButtonColumn(TableColumn<T, Void> column, String buttonText,
                                              OneArgKnob<T> buttonClickHandler, OneArgKnob<Button> buttonTuner) {
        column.setCellFactory(new Callback<TableColumn<T, Void>, TableCell<T, Void>>() {
            @Override
            public TableCell<T, Void> call(final TableColumn<T, Void> param) {
                return new TableCell<T, Void>() {
                    private final Button button = new Button(buttonText);

                    {
                        if (buttonTuner != null)
                            buttonTuner.execute(button);
                        button.setOnAction((ActionEvent event) -> buttonClickHandler.execute(getTableView().getItems().get(getIndex())));
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(button);
                        }
                    }
                };
            }
        });
    }

    public static Date getDate(DatePicker datePicker) {
        return datePicker.getValue() != null ? java.sql.Date.valueOf(datePicker.getValue()) : null;
    }

    public static void setSize(Region region, Size size) {
        setWidth(region, size.getWidth());
        setHeight(region, size.getHeight());
    }

    public static void setWidth(Region region, int width) {
        region.setMaxWidth(width);
        region.setMinWidth(width);
    }

    public static void setHeight(Region region, int height) {
        region.setMaxHeight(height);
        region.setMinHeight(height);
    }
}
