package com.katyshevtseva.fx.dialog.controller;

import com.katyshevtseva.fx.Utils;
import com.katyshevtseva.fx.WindowBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


public class TwoFieldsEditDialogController implements WindowBuilder.FxController {
    @FXML
    private TextField textField;
    @FXML
    private TextArea textArea;
    @FXML
    private Button okButton;
    private OkButtonHandler okButtonHandler;
    private String initFirstText;
    private String initSecondText;

    public TwoFieldsEditDialogController(String initFirstText, String initSecondText, OkButtonHandler okButtonHandler) {
        this.okButtonHandler = okButtonHandler;
        this.initFirstText = initFirstText;
        this.initSecondText = initSecondText;
    }

    @FXML
    private void initialize() {
        Utils.associateButtonWithControls(okButton, textArea, textField);
        textField.setText(initFirstText);
        textArea.setText(initSecondText);
        okButton.setOnAction(event -> {
            okButtonHandler.execute(textField.getText(), textArea.getText());
            Utils.closeWindowThatContains(textField);
        });
    }

    @FunctionalInterface
    public interface OkButtonHandler {
        void execute(String firstText, String secondText);
    }
}
