package com.katyshevtseva.fx.dialog.controller;

import com.katyshevtseva.fx.FxUtils;
import com.katyshevtseva.fx.WindowBuilder.FxController;
import com.katyshevtseva.general.TwoArgKnob;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import lombok.Getter;

import java.util.List;

public class TextFieldAndComboBoxDialogController<T> implements FxController {
    @FXML
    @Getter
    private TextField textField;
    @FXML
    private ComboBox<T> comboBox;
    @FXML
    private Button okButton;
    private TwoArgKnob<String, T> okButtonHandler;
    private String initText;
    private List<T> comboBoxItems;
    private T initComboBoxSelectedItem;

    public TextFieldAndComboBoxDialogController(String initText, List<T> comboBoxItems,
                                                T initComboBoxSelectedItem, TwoArgKnob<String, T> okButtonHandler) {
        this.okButtonHandler = okButtonHandler;
        this.initText = initText;
        this.comboBoxItems = comboBoxItems;
        this.initComboBoxSelectedItem = initComboBoxSelectedItem;
    }

    @FXML
    private void initialize() {
        FxUtils.associateButtonWithControls(okButton, comboBox, textField);
        textField.setText(initText);
        comboBox.setItems(FXCollections.observableArrayList(comboBoxItems));
        if (initComboBoxSelectedItem != null)
            comboBox.setValue(initComboBoxSelectedItem);
        okButton.setOnAction(event -> {
            okButtonHandler.execute(textField.getText(), comboBox.getValue());
            FxUtils.closeWindowThatContains(textField);
        });
    }
}
