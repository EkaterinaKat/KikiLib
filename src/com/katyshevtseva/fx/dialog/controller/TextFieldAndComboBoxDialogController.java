package com.katyshevtseva.fx.dialog.controller;

import com.katyshevtseva.fx.Utils;
import com.katyshevtseva.fx.WindowBuilder.FxController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.List;

public class TextFieldAndComboBoxDialogController<T> implements FxController {
    @FXML
    private TextField textField;
    @FXML
    private ComboBox<T> comboBox;
    @FXML
    private Button okButton;
    private OkButtonHandler<T> okButtonHandler;
    private String initText;
    private List<T> comboBoxItems;
    private T initComboBoxSelectedItem;

    public TextFieldAndComboBoxDialogController(String initText, List<T> comboBoxItems,
                                                T initComboBoxSelectedItem, OkButtonHandler<T> okButtonHandler) {
        this.okButtonHandler = okButtonHandler;
        this.initText = initText;
        this.comboBoxItems = comboBoxItems;
        this.initComboBoxSelectedItem = initComboBoxSelectedItem;
    }

    @FXML
    private void initialize() {
        Utils.associateButtonWithControls(okButton, comboBox, textField);
        textField.setText(initText);
        comboBox.setItems(FXCollections.observableArrayList(comboBoxItems));
        comboBox.setValue(initComboBoxSelectedItem);
        okButton.setOnAction(event -> {
            okButtonHandler.execute(textField.getText(), comboBox.getValue());
            Utils.closeWindowThatContains(textField);
        });
    }

    @FunctionalInterface
    public interface OkButtonHandler<T> {
        void execute(String text, T comboBoxItem);
    }
}
