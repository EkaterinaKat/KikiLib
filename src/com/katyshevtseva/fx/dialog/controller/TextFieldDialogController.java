package com.katyshevtseva.fx.dialog.controller;

import com.katyshevtseva.fx.Utils;
import com.katyshevtseva.fx.WindowBuilder.FxController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class TextFieldDialogController implements FxController {
    @FXML
    private TextField textField;
    @FXML
    private Button okButton;
    private OkButtonHandler okButtonHandler;
    private String initText;

    public TextFieldDialogController(String initText, OkButtonHandler okButtonHandler) {
        this.okButtonHandler = okButtonHandler;
        this.initText = initText;
    }

    @FXML
    private void initialize() {
        Utils.associateButtonWithControls(okButton, textField);
        textField.setText(initText);
        okButton.setOnAction(event -> {
            okButtonHandler.execute(textField.getText());
            Utils.closeWindowThatContains(textField);
        });
    }

    @FunctionalInterface
    public interface OkButtonHandler {
        void execute(String text);
    }
}
