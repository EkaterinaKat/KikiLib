package com.katyshevtseva.fx;

import com.katyshevtseva.general.GeneralUtils;
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
        label.setStyle(Styler.getColorfullStyle(Styler.ThingToColor.TEXT, Styler.StandardColor.BLACK));

        if (!GeneralUtils.isEmpty(reportCell.getColor())) {
            Styler.setBackgroundColorAndCorrectTextColor(cell, label, reportCell.getColor());
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
        table.add(cell, column, row);
    }
}
