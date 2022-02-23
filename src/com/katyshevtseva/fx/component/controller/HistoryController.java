package com.katyshevtseva.fx.component.controller;

import com.katyshevtseva.fx.FxUtils;
import com.katyshevtseva.fx.Size;
import com.katyshevtseva.fx.Styler;
import com.katyshevtseva.fx.WindowBuilder.FxController;
import com.katyshevtseva.history.Action;
import com.katyshevtseva.history.HasHistory;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.katyshevtseva.date.DateUtils.READABLE_DATE_FORMAT;
import static com.katyshevtseva.fx.FxUtils.*;

class HistoryController<E extends HasHistory<A>, A extends Action<?>> implements FxController {
    private final Size size;
    private int blockWidth;
    @FXML
    private VBox historyPane;
    @FXML
    private ScrollPane scrollPane;

    public HistoryController(Size size) {
        this.size = size;
        setSizes();
    }

    private void setSizes() {
        blockWidth = size.getWidth() - 25;
        setSize(scrollPane, size);
    }

    public void show(E entity) {
        historyPane.getChildren().clear();
        historyPane.getChildren().add(getPaneWithHeight(20));
        List<A> actions = entity.getHistory().stream().sorted(Comparator.comparing(Action::getDate)).collect(Collectors.toList());
        for (Action action : actions) {
            historyPane.getChildren().add(actionToBlock(action));
            historyPane.getChildren().add(FxUtils.getPaneWithHeight(20));
        }
    }

    private Pane actionToBlock(Action action) {
        VBox vBox = new VBox();

        Label datesLabel = new Label(READABLE_DATE_FORMAT.format(action.getDate()));
        vBox.getChildren().addAll(getPaneWithHeight(15), datesLabel);

        Label descLabel = new Label(action.getDescription());
        descLabel.setWrapText(true);
        descLabel.setMaxWidth(blockWidth - 40);
        vBox.getChildren().addAll(getPaneWithHeight(15), descLabel, getPaneWithHeight(15));

        HBox hBox = new HBox();
        hBox.getChildren().addAll(getPaneWithWidth(15), vBox);
        hBox.setStyle(Styler.getBlackBorderStyle());
        setWidth(hBox, blockWidth);
        return hBox;
    }
}

