package com.katyshevtseva.fx.component.controller;

import com.katyshevtseva.fx.Size;
import com.katyshevtseva.fx.Styler;
import com.katyshevtseva.fx.WindowBuilder;
import com.katyshevtseva.general.OneArgKnob;
import com.katyshevtseva.general.TwoArgKnob;
import com.katyshevtseva.hierarchy.StaticHierarchySchemaLine;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.katyshevtseva.fx.FxUtils.setSize;

@RequiredArgsConstructor
public class StaticHierarchyController implements WindowBuilder.FxController {
    private final List<StaticHierarchySchemaLine> schema;
    private final Size size;
    private final TwoArgKnob<StaticHierarchySchemaLine, Label> nodeLabelAdjuster;
    private final OneArgKnob<StaticHierarchySchemaLine> clickHandler;
    @FXML
    private GridPane schemaBox;
    @FXML
    private VBox editGroupPane;
    @FXML
    private ScrollPane scrollPane;

    @FXML
    private void initialize() {
        editGroupPane.setVisible(false);
        setSize(scrollPane, size);
        fillSchema();
    }

    public void fillSchema() {
        schemaBox.getChildren().clear();
        for (int i = 0; i < schema.size(); i++) {
            addLineToSchemaBox(schema.get(i), i);
        }
    }

    private void addLineToSchemaBox(StaticHierarchySchemaLine line, int rowIndex) {

        Label entryLabel = new Label(line.getTitle());

        entryLabel.setStyle(Styler.getColorfullStyle(Styler.ThingToColor.TEXT, getColorByLevel(line.getLevel())));

        if (nodeLabelAdjuster != null) {
            nodeLabelAdjuster.execute(line, entryLabel);
        }
        entryLabel.setTooltip(new Tooltip(line.getDescription()));

        if (clickHandler != null) {
            entryLabel.setOnMouseClicked(event -> clickHandler.execute(line));
        }

        HBox hBox = new HBox();
        hBox.getChildren().add(entryLabel);

        schemaBox.add(hBox, line.getLevel(), rowIndex);
    }

    private String getColorByLevel(int level) {
        if (level == 0)
            return "#ff0000";
        if (level == 1)
            return "#0000ff";
        if (level == 2)
            return "#118f06";
        return "#000000";
    }
}
