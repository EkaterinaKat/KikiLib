package com.katyshevtseva.fx.dialog.controller;

import com.katyshevtseva.fx.Utils;
import com.katyshevtseva.fx.WindowBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class TwoTextFieldsDialogController implements WindowBuilder.FxController {
    @FXML
    private TextField textField1;
    @FXML
    private TextField textField2;
    @FXML
    private Button okButton;
    private OkButtonHandler okButtonHandler;
    private boolean closeAfterOk;
    private String initText1;
    private String initText2;

    public TwoTextFieldsDialogController(String initText1, String initText2, boolean closeAfterOk, OkButtonHandler okButtonHandler) {
        this.okButtonHandler = okButtonHandler;
        this.closeAfterOk = closeAfterOk;
        this.initText1 = initText1;
        this.initText2 = initText2;
    }

    @FXML
    private void initialize() {
        Utils.associateButtonWithControls(okButton, textField1, textField2);
        textField1.setText(initText1);
        textField2.setText(initText2);
        okButton.setOnAction(event -> okButtonListener());
        textField1.setOnAction(event -> textField2.requestFocus());
        textField2.setOnAction(event -> {
            if (!okButton.isDisable())
                okButtonListener();
        });
    }

    private void okButtonListener() {
        okButtonHandler.execute(textField1.getText(), textField2.getText());
        if (closeAfterOk)
            Utils.closeWindowThatContains(textField1);
    }

    @FunctionalInterface
    public interface OkButtonHandler {
        void execute(String text1, String text2);
    }

    public TextField getTextField1() {
        return textField1;
    }

    public TextField getTextField2() {
        return textField2;
    }
}
