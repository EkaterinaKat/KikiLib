package com.katyshevtseva.fx.dialog.controller;

import com.katyshevtseva.fx.Size;
import com.katyshevtseva.fx.WindowBuilder.FxController;
import com.katyshevtseva.fx.component.ComponentBuilder;
import com.katyshevtseva.fx.component.ComponentBuilder.Component;
import com.katyshevtseva.fx.component.controller.HistoryController;
import com.katyshevtseva.history.Action;
import com.katyshevtseva.history.HasHistory;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HistoryDialogController<E extends HasHistory<A>, A extends Action<E>> implements FxController {
    private final E entity;
    private final Size size;
    @FXML
    private Pane historyPane;

    @FXML
    private void initialize() {
        Component<HistoryController<E, A>> component = new ComponentBuilder()
                .setSize(new Size(size.getHeight() - 60, size.getWidth() - 25))
                .getHistoryComponent();
        historyPane.getChildren().add(component.getNode());
        component.getController().show(entity);
    }
}
