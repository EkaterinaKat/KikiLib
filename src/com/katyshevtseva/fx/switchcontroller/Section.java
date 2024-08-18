package com.katyshevtseva.fx.switchcontroller;

import com.katyshevtseva.fx.WindowBuilder.FxController;
import com.katyshevtseva.general.OneInOneOutKnob;
import javafx.scene.Node;
import javafx.scene.control.Button;
import lombok.Getter;

public class Section {
    @Getter
    private final SectionController controller;
    private final OneInOneOutKnob<FxController, Node> nodeSupplier;
    private Node node;
    @Getter
    private final Button button;
    @Getter
    private final Boolean disabled;

    public Section(String title,
                   SectionController controller,
                   OneInOneOutKnob<FxController, Node> nodeSupplier,
                   boolean disabled) {
        this.controller = controller;
        this.nodeSupplier = nodeSupplier;
        button = new Button(title);
        this.disabled = disabled;
    }

    public Section(String title, SectionController controller, OneInOneOutKnob<FxController, Node> nodeSupplier) {
        this(title, controller, nodeSupplier, false);
    }

    public Node getNode() {
        if (node == null) {
            node = nodeSupplier.execute(controller);
        }
        return node;
    }
}
