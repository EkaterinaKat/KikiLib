package com.katyshevtseva.fx.dialog.controller;

import com.katyshevtseva.fx.FxUtils;
import com.katyshevtseva.fx.WindowBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class InfoDialogController implements WindowBuilder.FxController {
    @FXML
    private Button okButton;
    @FXML
    private Label infoLabel;
    private final String info;

    public InfoDialogController(String info) {
        this.info = info;
    }

    @FXML
    private void initialize() {
        infoLabel.setText(info);
        okButton.setOnAction(event -> FxUtils.closeWindowThatContains(infoLabel));
    }
}
