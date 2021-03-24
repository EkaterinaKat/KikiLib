package com.katyshevtseva.collage.old;

import com.katyshevtseva.fx.Point;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;

class ImageModification {
    private CollageComponent component;
    private ModificationType modificationType;
    private Point cursorInitCoord;
    private Point imageInitCoord;

    static ImageModification getModificationIfNeededOrNull(CollageComponent component, MouseEvent dragEvent) {
        Point dragStartPoint = new Point(dragEvent.getX(), dragEvent.getY());
        boolean sizeAdjusterContainPoint = component.sizeAdjusterContainsPoint(dragStartPoint);
        if (sizeAdjusterContainPoint) {
            return new ImageModification(ModificationType.RESIZING, dragStartPoint, component);
        }
        boolean imageContainsPoint = component.imageContainsPoint(dragStartPoint);
        if (imageContainsPoint) {
            return new ImageModification(ModificationType.MOVING, dragStartPoint, component);
        }
        return null;
    }

    void reportDragEvent(DragEvent event) {
        if (modificationType == ModificationType.MOVING) {
            relocateIfAllowable(event);
        } else if (modificationType == ModificationType.RESIZING) {
            resizeIfAllowable(event);
        }
    }

    private ImageModification(ModificationType modificationType, Point dragStartPoint, CollageComponent component) {
        this.component = component;
        this.modificationType = modificationType;
        this.cursorInitCoord = dragStartPoint;
        imageInitCoord = new Point(component.getX(), component.getY());
    }

    private enum ModificationType {
        RESIZING, MOVING
    }

    private void relocateIfAllowable(DragEvent event) {
        Point newCoord = new Point(event.getX() - cursorInitCoord.getX() + imageInitCoord.getX(),
                event.getY() - cursorInitCoord.getY() + imageInitCoord.getY());
        if (newCoord.getX() > 0 && newCoord.getY() > 0 && component.relocationAllowable(newCoord)) {
            component.setCoordinates(newCoord);
        }
    }

    private void resizeIfAllowable(DragEvent event) {
        double newWidth = event.getX() - component.getX();
        double newHeight = component.getNewHeightByNewWidth(newWidth);
        if (component.resizeAllowable(newWidth, newHeight)) {
            component.setNewSize(newWidth, newHeight);
        }
    }
}
