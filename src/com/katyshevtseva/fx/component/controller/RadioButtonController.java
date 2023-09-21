package com.katyshevtseva.fx.component.controller;

import com.katyshevtseva.fx.FxUtils;
import com.katyshevtseva.fx.WindowBuilder;
import com.katyshevtseva.general.OneArgKnob;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RadioButtonController<E> implements WindowBuilder.FxController {
    @FXML
    private HBox root;
    @Getter
    private E selectedItem;
    private final OneArgKnob<E> selectionListener;
    private final Map<E, Button> buttons = new HashMap<>();

    public RadioButtonController(List<E> items, E selectedItem, OneArgKnob<E> selectionListener) {
        this.selectedItem = selectedItem;
        this.selectionListener = selectionListener;

        for (E item : items) {
            Button button = new Button(item.toString());
            buttons.put(item, button);
            button.setOnAction(event -> selectItem(item));
            if (item.equals(selectedItem)) {
                selectItem(item);
            }
        }
    }

    @FXML
    private void initialize() {
        root.getChildren().add(FxUtils.getPaneWithWidth(10));
        for (Button button : buttons.values()) {
            root.getChildren().add(button);
            root.getChildren().add(FxUtils.getPaneWithWidth(10));
        }
        root.setStyle(root.getStyle() + " -fx-border-radius: 5px; -fx-border-color: #000000; ");
    }

    public void selectItem(E item) {
        selectedItem = item;
        for (Button button : buttons.values()) {
            button.setDisable(false);
        }
        buttons.get(item).setDisable(true);
        selectionListener.execute(item);
    }
}
