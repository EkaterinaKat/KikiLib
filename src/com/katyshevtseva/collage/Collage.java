package com.katyshevtseva.collage;

import com.katyshevtseva.fx.Styler;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Collage {
    private List<Component> components = new ArrayList<>();
    private Pane collagePane;
    private ComponentModification currentModification;
    private boolean editingMode;

    public Collage(int height, int width) {
        initializeCollagePane(height, width);
        tuneComponentModificationMechanism();
    }

    public double getHeight() {
        return collagePane.getHeight();
    }

    public double getWidth() {
        return collagePane.getWidth();
    }

    public Pane getCollagePane() {
        return collagePane;
    }

    public void addComponent(Component component) {
        components.add(component);
        moveComponentToFirstPlanAndRefillCollage(component);
    }

    public void setEditingMode(boolean editingMode) {
        this.editingMode = editingMode;
        clearAndRefillPaneWithComponents();
    }

    /////////////////////////////   END OF API  ///////////////////////////////////////////////////

    private void initializeCollagePane(double height, double width) {
        Pane pane = new Pane();
        pane.setMaxWidth(width);
        pane.setMinWidth(width);
        pane.setMaxHeight(height);
        pane.setMinHeight(height);
        pane.setStyle(Styler.getColorfullStyle(Styler.ThingToColor.BACKGROUND, "#7B68EE"));
        collagePane = pane;
    }

    void moveComponentToFirstPlanAndRefillCollage(Component component) {
        component.setZ(getImagesMaxZ() + 1);
        normalizeZCoordinates();
        clearAndRefillPaneWithComponents();
    }

    private void clearAndRefillPaneWithComponents() {
        collagePane.getChildren().clear();
        components.sort(Comparator.comparing(Component::getZ)); //todo правильно ли сортируется
        for (Component component : components) {
            if (editingMode)
                collagePane.getChildren().addAll(component.getImageViewWithButtons());
            else
                collagePane.getChildren().add(component.getImageView());
        }
    }

    void deleteComponent(Component component) {
        components.remove(component);
        clearAndRefillPaneWithComponents();
    }

    private void tuneComponentModificationMechanism() {
        collagePane.setOnDragDetected(event -> {
            if (!editingMode)
                return;

            Dragboard db = collagePane.startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            content.putString("");
            db.setContent(content);
            event.consume();
            for (Component component : components.stream().sorted(Comparator.comparing(Component::getZ).reversed()).collect(Collectors.toList())) {
                currentModification = ComponentModification.getModificationIfNeededOrNull(component, event);
                if (currentModification != null) {
                    moveComponentToFirstPlanAndRefillCollage(component);
                    break;
                }
            }

        });

        collagePane.setOnDragOver(event -> {
            if (currentModification != null) {
                currentModification.reportDragEvent(event);
            }
        });

        collagePane.setOnDragDone(event -> {
            currentModification = null;
            event.consume();
        });
    }

    boolean isEditingMode() {
        return editingMode;
    }

    private int getImagesMaxZ() {
        int result = 0;
        for (Component component : components)
            if (component.getZ() > result)
                result = component.getZ();
        return result;
    }

    private void normalizeZCoordinates() {
        components.sort(Comparator.comparing(Component::getZ));
        for (int i = 0; i < components.size(); i++)
            components.get(i).setZ(i + 1);
    }
}
