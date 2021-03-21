package com.katyshevtseva.collage;

import com.katyshevtseva.fx.Styler;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Collage {
    private List<CollageComponent> components = new ArrayList<>();
    private int height;
    private int width;
    private Pane collagePane;
    private ImageModification currentModification;
    private boolean editingMode;

    public Collage(int height, int width, boolean editingMode) {
        this.editingMode = editingMode;
        this.height = height;
        this.width = width;
        initializeCollagePane();
        tune();
    }

    public Pane getCollagePane() {
        return collagePane;
    }

    public void addComponent(CollageComponent collageComponent) {
        components.add(collageComponent);
        moveComponentToFirstPlan(collageComponent);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void setEditingMode(boolean editingMode) {
        this.editingMode = editingMode;
        addComponentsOnPane();
    }

    /////////////////////////////   END OF API  ///////////////////////////////////////////////////

    private void initializeCollagePane() {
        Pane pane = new Pane();
        pane.setMaxWidth(width);
        pane.setMinWidth(width);
        pane.setMaxHeight(height);
        pane.setMinHeight(height);
        pane.setStyle(Styler.getColorfullStyle(Styler.ThingToColor.BACKGROUND, "#023648"));
        collagePane = pane;
    }

    private void addComponentsOnPane() {
        collagePane.getChildren().clear();
        components.sort(Comparator.comparing(CollageComponent::getZ));
        for (CollageComponent component : components) {
            if (editingMode)
                collagePane.getChildren().addAll(component.getImageViewWithButtons());
            else
                collagePane.getChildren().add(component.getImageView());
        }
    }

    private void tune() {

        collagePane.setOnDragDetected(event -> {
            if (editingMode) {
                Dragboard db = collagePane.startDragAndDrop(TransferMode.ANY);
                ClipboardContent content = new ClipboardContent();
                content.putString("");
                db.setContent(content);
                event.consume();
                for (CollageComponent component : components) {
                    currentModification = ImageModification.getModificationIfNeededOrNull(component, event);
                    if (currentModification != null) {
                        moveComponentToFirstPlan(component);
                        break;
                    }
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

    void moveComponentToFirstPlan(CollageComponent component) {
        collagePane.getChildren().removeAll(component.getImageViewWithButtons());
        collagePane.getChildren().addAll(component.getImageViewWithButtons());
        component.setZ(getImagesMaxZ() + 1);
        normalizeZCoordinates();
        components.sort(Comparator.comparing(CollageComponent::getZ).reversed());
    }

    void deleteComponent(CollageComponent component) {
        components.remove(component);
        collagePane.getChildren().removeAll(component.getImageViewWithButtons());
    }

    private int getImagesMaxZ() {
        int result = 0;
        for (CollageComponent component : components)
            if (component.getZ() > result)
                result = component.getZ();
        return result;
    }

    private void normalizeZCoordinates() {
        components.sort(Comparator.comparing(CollageComponent::getZ));
        for (int i = 0; i < components.size(); i++) {
            components.get(i).setZ(i + 1);
        }
    }

    boolean isEditingMode() {
        return editingMode;
    }
}
