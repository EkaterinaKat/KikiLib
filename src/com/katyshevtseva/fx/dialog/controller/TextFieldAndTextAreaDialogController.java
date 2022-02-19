package com.katyshevtseva.fx.dialog.controller;

import com.katyshevtseva.fx.FxUtils;
import com.katyshevtseva.fx.Size;
import com.katyshevtseva.fx.WindowBuilder;
import com.katyshevtseva.general.TwoArgKnob;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import lombok.RequiredArgsConstructor;

import static com.katyshevtseva.fx.FxUtils.setSize;
import static com.katyshevtseva.fx.FxUtils.setWidth;

@RequiredArgsConstructor
public class TextFieldAndTextAreaDialogController implements WindowBuilder.FxController {
    private final String initFirstText;
    private final String initSecondText;
    private final TwoArgKnob<String, String> okButtonHandler;
    private final Size windowSize;
    @FXML
    private TextField textField;
    @FXML
    private TextArea textArea;
    @FXML
    private Button okButton;

    @FXML
    private void initialize() {
        setSizes();
        FxUtils.associateButtonWithControls(okButton, textArea, textField);
        textField.setText(initFirstText);
        textArea.setText(initSecondText);
        okButton.setOnAction(event -> {
            okButtonHandler.execute(textField.getText(), textArea.getText());
            FxUtils.closeWindowThatContains(textField);
        });
    }

    private void setSizes() {
        setWidth(textField, windowSize.getWidth() - 90);
        setSize(textArea, new Size(windowSize.getHeight() - 190, windowSize.getWidth() - 90));
    }
}
