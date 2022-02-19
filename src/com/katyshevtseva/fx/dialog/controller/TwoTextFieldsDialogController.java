package com.katyshevtseva.fx.dialog.controller;

import com.katyshevtseva.fx.FxUtils;
import com.katyshevtseva.fx.WindowBuilder;
import com.katyshevtseva.general.TwoArgKnob;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TwoTextFieldsDialogController implements WindowBuilder.FxController {
    private final String initText1;
    private final String initText2;
    private final boolean closeAfterOk;
    private final TwoArgKnob<String, String> okButtonHandler;
    @FXML
    private TextField textField1;
    @FXML
    private TextField textField2;
    @FXML
    private Button okButton;

    @FXML
    private void initialize() {
        FxUtils.associateButtonWithControls(okButton, textField1, textField2);
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
            FxUtils.closeWindowThatContains(textField1);
        else {
            textField1.clear();
            textField2.clear();
            textField1.requestFocus();
        }
    }

    public TextField getTextField1() {
        return textField1;
    }

    public TextField getTextField2() {
        return textField2;
    }
}
