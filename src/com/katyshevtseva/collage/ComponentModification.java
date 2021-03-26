package com.katyshevtseva.collage;

import com.katyshevtseva.fx.Point;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;

class ComponentModification {
    private Component component;
    private ModificationType modificationType;
    private Point cursorInitCoord;
    private Point imageInitCoord;

    static ComponentModification getModificationIfNeededOrNull(Component component, MouseEvent dragEvent) {
        Point dragStartPoint = new Point(dragEvent.getX(), dragEvent.getY());

        boolean sizeAdjusterContainPoint = component.sizeAdjusterContainsPoint(dragStartPoint);
        if (sizeAdjusterContainPoint) {
            return new ComponentModification(ModificationType.RESIZING, dragStartPoint, component);
        }

        boolean imageContainsPoint = component.imageContainsPoint(dragStartPoint);
        if (imageContainsPoint) {
            return new ComponentModification(ModificationType.MOVING, dragStartPoint, component);
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

    private ComponentModification(ModificationType modificationType, Point dragStartPoint, Component component) {
        this.component = component;
        this.modificationType = modificationType;
        this.cursorInitCoord = dragStartPoint;
        imageInitCoord = component.getPos();
    }

    private enum ModificationType {
        RESIZING, MOVING
    }

    private void relocateIfAllowable(DragEvent event) {
        Point newCoord = new Point(event.getX() - cursorInitCoord.getX() + imageInitCoord.getX(),
                event.getY() - cursorInitCoord.getY() + imageInitCoord.getY());
        component.relocateIfAllowable(newCoord);
    }

    private void resizeIfAllowable(DragEvent event) {
        double newWidth = event.getX() - component.getPos().getX();
        component.resizeIfAllowable(newWidth);
    }
}
