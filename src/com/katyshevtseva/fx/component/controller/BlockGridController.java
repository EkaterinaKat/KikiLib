package com.katyshevtseva.fx.component.controller;

import com.katyshevtseva.fx.FxUtils;
import com.katyshevtseva.fx.Size;
import com.katyshevtseva.fx.WindowBuilder.FxController;
import com.katyshevtseva.general.OneArgKnob;
import com.katyshevtseva.general.OneInOneOutKnob;
import com.katyshevtseva.general.TwoInOneOutKnob;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.List;

import static com.katyshevtseva.general.GeneralUtils.getColumnByIndexAndColumnNum;
import static com.katyshevtseva.general.GeneralUtils.getRowByIndexAndColumnNum;

public class BlockGridController<E> implements FxController {
    private static final int FRAME_SIZE = 20;
    private static final int GAP_SIZE = 10;

    private final int blockWidth;
    private final Size scrollPaneSize;
    private final int columnNum;
    private final OneArgKnob<E> clickHandler;
    private final OneInOneOutKnob<E, ContextMenu> contextMenuSupplier;
    private final TwoInOneOutKnob<E, Integer, Node> blockSupplier;
    private List<E> items;

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private GridPane gridPane;
    @FXML
    private Pane leftPane;
    @FXML
    private Pane topPane;

    public BlockGridController(int blockWidth, Size scrollPaneSize, OneArgKnob<E> clickHandler,
                               OneInOneOutKnob<E, ContextMenu> contextMenuSupplier,
                               TwoInOneOutKnob<E, Integer, Node> blockSupplier) {
        this.blockWidth = blockWidth;
        this.scrollPaneSize = scrollPaneSize;
        this.clickHandler = clickHandler;
        this.contextMenuSupplier = contextMenuSupplier;
        this.blockSupplier = blockSupplier;
        columnNum = (scrollPaneSize.getWidth() - (2 * FRAME_SIZE) + GAP_SIZE) / (blockWidth + GAP_SIZE);
    }

    @FXML
    private void initialize() {
        gridPane.setVgap(GAP_SIZE);
        gridPane.setHgap(GAP_SIZE);
        FxUtils.setWidth(leftPane, FRAME_SIZE);
        FxUtils.setHeight(topPane, FRAME_SIZE);
        FxUtils.setSize(scrollPane, scrollPaneSize);
    }

    public void setContent(List<E> items) {
        this.items = items;
        fillGridPane();
    }

    private void fillGridPane() {
        gridPane.getChildren().clear();

        for (int i = 0; i < items.size(); i++) {
            addBlockToGridPane(i);
        }
    }

    private void addBlockToGridPane(int index) {
        E item = items.get(index);
        Node node = blockSupplier.execute(item, blockWidth);

        if (clickHandler != null) {
            node.setOnMouseClicked(event -> clickHandler.execute(item));
        }
        if (contextMenuSupplier != null) {
            node.setOnContextMenuRequested(event -> {
                ContextMenu contextMenu = contextMenuSupplier.execute(item);
                contextMenu.show(node, event.getScreenX(), event.getScreenY());
            });
        }

        gridPane.add(node,
                getColumnByIndexAndColumnNum(index, columnNum),
                getRowByIndexAndColumnNum(index, columnNum));

        GridPane.setHalignment(node, HPos.CENTER);
        GridPane.setValignment(node, VPos.CENTER);
    }
}