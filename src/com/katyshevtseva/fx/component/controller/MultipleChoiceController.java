package com.katyshevtseva.fx.component.controller;

import com.katyshevtseva.fx.FxUtils;
import com.katyshevtseva.fx.Size;
import com.katyshevtseva.fx.WindowBuilder.FxController;
import com.katyshevtseva.fx.dialogconstructor.DcComboBox;
import com.katyshevtseva.fx.dialogconstructor.DialogConstructor;
import com.katyshevtseva.general.GeneralUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MultipleChoiceController<E> implements FxController {
    private final Size size;
    private final List<E> items;
    private final List<E> selectedItems;
    private ItemSupplier<E> customItemSupplier;
    private boolean emptyListIsForbidden;
    @FXML
    private ScrollPane root;
    @FXML
    private VBox mainPane;

    public MultipleChoiceController(List<E> items, Size size, List<E> selectedItems, boolean emptyListIsForbidden) {
        if (emptyListIsForbidden && GeneralUtils.isEmpty(selectedItems)) {
            throw new RuntimeException("Try to set empty list while empty list is forbidden");
        }

        this.items = items;
        this.size = size;
        this.emptyListIsForbidden = emptyListIsForbidden;
        this.selectedItems = selectedItems != null ? selectedItems : new ArrayList<>();
    }

    public List<E> getSelectedItems() {
        return selectedItems;
    }

    public List<E> getAllItems() {
        return items;
    }

    public void setCustomItemSupplier(ItemSupplier<E> itemSupplier) {
        customItemSupplier = itemSupplier;
    }

    public void clear() {
        if (emptyListIsForbidden) {
            throw new RuntimeException("Try to clear list while empty list is forbidden");
        }
        selectedItems.clear();
        fillMainPane();
    }

    @FunctionalInterface
    public interface ItemSupplier<E> {
        E getItem();
    }

    public void forbidEmptyList(E initItem) {
        emptyListIsForbidden = true;
        selectedItems.add(initItem);
        fillMainPane();
    }

    //////////////////////////////////  END OF API  //////////////////////////////////////////////////

    @FXML
    private void initialize() {
        root.setMinHeight(size.getHeight());
        root.setMaxHeight(size.getHeight());
        root.setMinWidth(size.getWidth());
        root.setMaxWidth(size.getWidth());
        fillMainPane();
    }

    private void fillMainPane() {
        mainPane.getChildren().clear();
        mainPane.getChildren().addAll(selectedItems.stream().map(this::itemToNode).collect(Collectors.toList()));
        mainPane.getChildren().add(getAddLabel());
    }

    private HBox itemToNode(E item) {
        Label titleLabel = new Label(item.toString());
        HBox hBox = new HBox();
        hBox.getChildren().add(titleLabel);

        if (selectedItems.size() > 1) {
            Label minusLabel = new Label("<->");
            minusLabel.setOnMouseClicked(event -> {
                selectedItems.remove(item);
                fillMainPane();
            });
            hBox.getChildren().addAll(FxUtils.getPaneWithWidth(10), minusLabel);
        }

        return hBox;
    }

    private Label getAddLabel() {
        Label label = new Label("<+>");
        if (customItemSupplier == null) {
            label.setOnMouseClicked(event -> {
                DcComboBox<E> comboBox = new DcComboBox<>(true, null, getNotSelectedItems());
                DialogConstructor.constructDialog(() -> {
                    selectedItems.add(comboBox.getValue());
                    fillMainPane();
                }, comboBox);
            });

        } else {
            selectedItems.add(customItemSupplier.getItem());
            fillMainPane();
        }
        return label;
    }

    private List<E> getNotSelectedItems() {
        return items.stream().filter(e -> !selectedItems.contains(e)).collect(Collectors.toList());
    }
}
