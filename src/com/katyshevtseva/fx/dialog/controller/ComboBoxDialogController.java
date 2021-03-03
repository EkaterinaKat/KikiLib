package com.katyshevtseva.fx.dialog.controller;

import com.katyshevtseva.fx.Utils;
import com.katyshevtseva.fx.WindowBuilder.FxController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import java.util.List;

public class ComboBoxDialogController<T> implements FxController {
    @FXML
    private ComboBox<T> comboBox;
    @FXML
    private Button okButton;
    private OkButtonHandler<T> okButtonHandler;
    private List<T> comboBoxItems;
    private T initComboBoxSelectedItem;

    public ComboBoxDialogController(List<T> comboBoxItems, T initComboBoxSelectedItem, OkButtonHandler<T> okButtonHandler) {
        this.okButtonHandler = okButtonHandler;
        this.comboBoxItems = comboBoxItems;
        this.initComboBoxSelectedItem = initComboBoxSelectedItem;
    }

    @FXML
    private void initialize() {
        Utils.associateButtonWithControls(okButton, comboBox);
        comboBox.setItems(FXCollections.observableArrayList(comboBoxItems));
        if (initComboBoxSelectedItem != null)
            comboBox.setValue(initComboBoxSelectedItem);
        okButton.setOnAction(event -> {
            okButtonHandler.execute(comboBox.getValue());
            Utils.closeWindowThatContains(comboBox);
        });
    }

    @FunctionalInterface
    public interface OkButtonHandler<T> {
        void execute(T comboBoxItem);
    }
}
