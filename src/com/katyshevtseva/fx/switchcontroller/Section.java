package com.katyshevtseva.fx.switchcontroller;

import com.katyshevtseva.fx.WindowBuilder.FxController;
import com.katyshevtseva.general.OneInOneOutKnob;
import javafx.scene.Node;
import javafx.scene.control.Button;
import lombok.Getter;

public class Section {
    private final FxController controller;
    private final OneInOneOutKnob<FxController, Node> nodeSupplier;
    private Node node;
    @Getter
    private final Button button;

    public Section(String title, FxController controller, OneInOneOutKnob<FxController, Node> nodeSupplier) {
        this.controller = controller;
        this.nodeSupplier = nodeSupplier;
        button = new Button(title);
    }

    public Node getNode() {
        if (node == null) {
            node = nodeSupplier.execute(controller);
        }
        return node;
    }
}
