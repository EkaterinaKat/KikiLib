package com.katyshevtseva.fx.component.controller;

import com.katyshevtseva.fx.FxUtils;
import com.katyshevtseva.fx.Size;
import com.katyshevtseva.fx.WindowBuilder.FxController;
import com.katyshevtseva.fx.component.ComponentBuilder;
import com.katyshevtseva.fx.component.ComponentBuilder.Component;
import com.katyshevtseva.fx.component.controller.PaginationPaneController.PageSource;
import com.katyshevtseva.general.TwoInOneOutKnob;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import static com.katyshevtseva.fx.FxUtils.getPaneWithHeight;
import static com.katyshevtseva.fx.FxUtils.setSize;

@RequiredArgsConstructor
public class BlockListController<E> implements FxController {
    private final Size size;
    private int blockWidth;
    @FXML
    private VBox contentPane;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Pane paginationPane;

    @FXML
    private void initialize() {
        setSizes();
    }

    private void setSizes() {
        blockWidth = size.getWidth() - 60;
        setSize(scrollPane, size);
    }

    public void show(PageSource<E> pageSource, TwoInOneOutKnob<E, Integer, Node> blockSupplier) {
        //tunePagination
        Component<PaginationPaneController<E>> component =
                new ComponentBuilder().getPaginationComponent(pageSource, items -> show(items, blockSupplier));
        paginationPane.getChildren().clear();
        paginationPane.getChildren().add(component.getNode());
    }

    private void show(List<E> items, TwoInOneOutKnob<E, Integer, Node> blockSupplier) {
        show(items.stream().map(item -> blockSupplier.execute(item, blockWidth)).collect(Collectors.toList()));
    }

    private void show(List<Node> nodes) {
        contentPane.getChildren().clear();
        contentPane.getChildren().add(getPaneWithHeight(20));
        for (Node node : nodes) {
            contentPane.getChildren().add(FxUtils.getPaneWithHeight(20));
            contentPane.getChildren().add(node);
        }
        contentPane.getChildren().add(FxUtils.getPaneWithHeight(20));
    }
}
