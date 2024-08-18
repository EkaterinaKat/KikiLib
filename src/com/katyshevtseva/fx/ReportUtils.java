package com.katyshevtseva.fx;

import com.katyshevtseva.general.ReportCell;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.List;

import static com.katyshevtseva.fx.Styler.getColorfullStyle;

public class ReportUtils {

    public static void showReport(List<List<ReportCell>> report, GridPane table, boolean rotateHeadline) {
        table.getChildren().clear();

        for (int i = 0; i < report.size(); i++) {
            List<ReportCell> reportLine = report.get(i);
            for (int j = 0; j < reportLine.size(); j++) {
                ReportCell reportCell = reportLine.get(j);
                fillCell(table, reportCell, i, j, rotateHeadline);
            }
        }
    }

    private static void fillCell(GridPane table, ReportCell reportCell, int row, int column, boolean rotateHeadline) {
        StackPane cell = new StackPane();

        Label label = new Label(reportCell.getText());
        label.setPadding(new Insets(5));
        label.setTooltip(new Tooltip(reportCell.getText()));
        label.setStyle(getColorfullStyle(Styler.ThingToColor.TEXT, Styler.StandardColor.BLACK));

        cell.setStyle(getColorfullStyle(Styler.ThingToColor.BACKGROUND, reportCell.getColor()));

        if (reportCell.getTextColor() == null) {
            Styler.correctLabelColorIfNeeded(label, reportCell.getColor());
        } else {
            label.setStyle(Styler.getColorfullStyle(Styler.ThingToColor.TEXT, reportCell.getTextColor()));
        }

        if (reportCell.isColumnHead()) {
            VBox vBox = new VBox(label);

            vBox.setPadding(new Insets(5, 5, 5, 5));
            if (rotateHeadline) {
                vBox.setRotate(90);
                cell.setPrefHeight(170);
            }
            cell.setPrefWidth(50);
            cell.getChildren().add(new Group(vBox));
        } else {
            cell.setPrefWidth(50);
            cell.getChildren().add(label);
        }

        if (reportCell.getWidth() != null) {
            cell.setPrefWidth(reportCell.getWidth());
            label.setMaxWidth(reportCell.getWidth());
            label.setWrapText(true);
        }

        HBox.setMargin(label, new Insets(8));
        StackPane.setAlignment(label, Pos.CENTER);

        if (reportCell.getContextMenu() != null) {
            cell.setOnContextMenuRequested(event -> reportCell.getContextMenu()
                    .show(cell, event.getScreenX(), event.getScreenY()));
        }

        table.add(cell, column, row);
    }
}
