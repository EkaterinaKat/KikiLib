package com.katyshevtseva.fx.dialog.controller;

import com.katyshevtseva.fx.WindowBuilder.FxController;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NodeContainerController implements FxController {
    private final Node node;
    @FXML
    private VBox container;

    @FXML
    private void initialize() {
        container.getChildren().add(node);
    }
}
