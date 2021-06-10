package com.katyshevtseva.fx.component;

import com.katyshevtseva.fx.DesignInfo;
import com.katyshevtseva.fx.FxUtils;
import com.katyshevtseva.fx.Size;
import com.katyshevtseva.fx.WindowBuilder.FxController;
import com.katyshevtseva.fx.dialog.StandardDialogBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MultipleChoiceController<E> implements FxController {
    private DesignInfo designInfo;
    private Size size;
    private List<E> items;
    private List<E> selectedItems = new ArrayList<>();
    private ItemSupplier<E> customItemSupplier;
    @FXML
    private ScrollPane root;
    @FXML
    private VBox mainPane;

    MultipleChoiceController(List<E> items, Size size, DesignInfo designInfo) {
        this.items = items;
        this.size = size;
        this.designInfo = designInfo;
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

    @FunctionalInterface
    public interface ItemSupplier<E> {
        E getItem();
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
        Label minusLabel = new Label("<->");
        minusLabel.setOnMouseClicked(event -> {
            selectedItems.remove(item);
            fillMainPane();
        });
        HBox hBox = new HBox();
        hBox.getChildren().addAll(titleLabel, FxUtils.getPaneWithWidth(10), minusLabel);
        return hBox;
    }

    private Label getAddLabel() {
        Label label = new Label("<+>");
        if (customItemSupplier == null) {
            label.setOnMouseClicked(event ->
                    new StandardDialogBuilder()
                            .setCssPath(designInfo.getCssPath())
                            .setIconPath(designInfo.getIconPath())
                            .openComboBoxDialog(getNotSelectedItems(), null, comboBoxItem -> {
                                selectedItems.add(comboBoxItem);
                                fillMainPane();
                            }));
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
