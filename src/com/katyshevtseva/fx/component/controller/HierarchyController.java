package com.katyshevtseva.fx.component.controller;

import com.katyshevtseva.fx.FxUtils;
import com.katyshevtseva.fx.Size;
import com.katyshevtseva.fx.Styler;
import com.katyshevtseva.fx.WindowBuilder.FxController;
import com.katyshevtseva.fx.dialog.StandardDialogBuilder;
import com.katyshevtseva.hierarchy.Group;
import com.katyshevtseva.hierarchy.HierarchyNode;
import com.katyshevtseva.hierarchy.HierarchySchemaService.AddButton;
import com.katyshevtseva.hierarchy.HierarchySchemaService.Entry;
import com.katyshevtseva.hierarchy.HierarchySchemaService.SchemaLine;
import com.katyshevtseva.hierarchy.HierarchyService;
import com.katyshevtseva.hierarchy.SchemaException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.katyshevtseva.fx.FxUtils.associateButtonWithControls;
import static com.katyshevtseva.fx.FxUtils.setComboBoxItems;

@RequiredArgsConstructor
public class HierarchyController implements FxController {
    private static final double RATIO_OF_SCHEMA_WIDTH_TO_TABLE_WIDTH = 3.0 / 4.0;
    private static final double RATIO_OF_TITLE_COLUMN_WIDTH_TO_BUTTON_COLUMN_WIDTH = 3.0 / 4.0;
    private final HierarchyService service;
    private final boolean editableSchema;
    private final Size size;
    @FXML
    private GridPane schemaBox;
    @FXML
    private Button addButton;
    @FXML
    private TextField nameTextField;
    @FXML
    private TableView<Group> table;
    @FXML
    private TableColumn<Group, String> titleColumn;
    @FXML
    private TableColumn<Group, Void> deleteColumn;
    @FXML
    private VBox editGroupPane;
    @FXML
    private ScrollPane scrollPane;

    @FXML
    private void initialize() {
        editGroupPane.setVisible(editableSchema);
        adjustSizes();
        adjustColumns();
        fillTable();
        fillSchema();
        associateButtonWithControls(addButton, nameTextField);
        addButton.setOnAction(event -> {
            service.createGroup(nameTextField.getText());
            nameTextField.clear();
            fillSchema();
            fillTable();
        });
    }

    private void adjustSizes() {
        if (editableSchema) {
            int groupEditPaneWidth = (int) (size.getWidth() * (1 - RATIO_OF_SCHEMA_WIDTH_TO_TABLE_WIDTH) - 20);
            int scrollPaneWidth = (int) (size.getWidth() * RATIO_OF_SCHEMA_WIDTH_TO_TABLE_WIDTH);

            scrollPane.setPrefHeight(size.getHeight());
            scrollPane.setPrefWidth(scrollPaneWidth);
            nameTextField.setPrefWidth(groupEditPaneWidth);
            table.setPrefWidth(groupEditPaneWidth);
            titleColumn.setPrefWidth(groupEditPaneWidth * RATIO_OF_TITLE_COLUMN_WIDTH_TO_BUTTON_COLUMN_WIDTH - 13);
            deleteColumn.setPrefWidth(groupEditPaneWidth * (1 - RATIO_OF_TITLE_COLUMN_WIDTH_TO_BUTTON_COLUMN_WIDTH) - 13);
        } else {
            scrollPane.setPrefHeight(size.getHeight());
            scrollPane.setPrefWidth(size.getWidth());
        }
    }

    private void fillSchema() {
        schemaBox.getChildren().clear();
        List<SchemaLine> schema = service.getSchema();
        for (int i = 0; i < schema.size(); i++) {
            addLineToSchemaBox(schema.get(i), i);
        }
    }

    private void addLineToSchemaBox(SchemaLine line, int rowIndex) {
        if (line instanceof AddButton && editableSchema) {
            AddButton addButton = (AddButton) line;

            ComboBox<HierarchyNode> comboBox = new ComboBox<>();
            comboBox.setVisible(false);
            setComboBoxItems(comboBox, service.getTopLevelNodes());
            comboBox.valueProperty().addListener(observable -> {
                try {
                    addButton.execute(comboBox.getValue());
                    fillSchema();
                } catch (SchemaException e) {
                    new StandardDialogBuilder().openInfoDialog(e.getMessage());
                    comboBox.setVisible(false);
                    e.printStackTrace();
                }
            });

            Label label = new Label("<+>");
            label.setOnMouseClicked(event -> comboBox.setVisible(true));

            HBox hBox = new HBox();
            hBox.getChildren().addAll(label, comboBox);
            schemaBox.add(hBox, addButton.getLevel(), rowIndex);

        } else if (line instanceof Entry) {
            HierarchyNode node = ((Entry) line).getNode();

            Label entryLabel = new Label(node.isLeaf() ? node.getTitle() : node.getTitle().toUpperCase());
            if (node.isLeaf()) {
                entryLabel.setStyle(Styler.getColorfullStyle(Styler.ThingToColor.TEXT, getColorByLevel(line.getLevel())) + Styler.getNotBoldTextStyle());
            } else {
                entryLabel.setStyle(Styler.getColorfullStyle(Styler.ThingToColor.TEXT, getColorByLevel(line.getLevel())));
            }
            entryLabel.setTooltip(new Tooltip(node.getDescription()));

            HBox hBox = new HBox();
            hBox.getChildren().add(entryLabel);

            if (!service.isTopLevel(node) && editableSchema) {
                Label deleteLabel = new Label("<->");
                deleteLabel.setOnMouseClicked(event -> {
                    service.deleteFromSchema(node);
                    fillSchema();
                });
                hBox.getChildren().add(deleteLabel);
            }

            schemaBox.add(hBox, line.getLevel(), rowIndex);
        }
    }

    private void adjustColumns() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        FxUtils.adjustButtonColumn(deleteColumn, "[x]",
                group ->
                        new StandardDialogBuilder().openQuestionDialog("Delete?", b -> {
                            if (b) {
                                service.destroyTreeAndDeleteGroup(group);
                                fillTable();
                                fillSchema();
                            }
                        }),
                button -> button.setMaxHeight(10));
    }

    private void fillTable() {
        ObservableList<Group> groups = FXCollections.observableArrayList();
        groups.addAll(service.getAllGroups());
        table.setItems(groups);
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
