package com.katyshevtseva.collage;

import com.katyshevtseva.fx.Point;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;

class ImageModification {
    private CollageImage image;
    private ModificationType modificationType;
    private Point cursorInitCoord;
    private Point imageInitCoord;

    static ImageModification getModificationIfNeededOrNull(CollageImage image, MouseEvent dragEvent) {
        Point dragStartPoint = new Point(dragEvent.getX(), dragEvent.getY());
        boolean sizeAdjusterContainPoint = image.sizeAdjusterContainsPoint(dragStartPoint);
        if (sizeAdjusterContainPoint) {
            return new ImageModification(ModificationType.RESIZING, dragStartPoint, image);
        }
        boolean imageContainsPoint = image.imageContainsPoint(dragStartPoint);
        if (imageContainsPoint) {
            return new ImageModification(ModificationType.MOVING, dragStartPoint, image);
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

    private ImageModification(ModificationType modificationType, Point dragStartPoint, CollageImage image) {
        this.image = image;
        this.modificationType = modificationType;
        this.cursorInitCoord = dragStartPoint;
        imageInitCoord = new Point(image.getX(), image.getY());
    }

    private enum ModificationType {
        RESIZING, MOVING
    }

    private void relocateIfAllowable(DragEvent event) {
        Point newCoord = new Point(event.getX() - cursorInitCoord.getX() + imageInitCoord.getX(),
                event.getY() - cursorInitCoord.getY() + imageInitCoord.getY());
        if (newCoord.getX() > 0 && newCoord.getY() > 0 && image.relocationAllowable(newCoord)) {
            image.setCoordinates(newCoord);
        }
    }

    private void resizeIfAllowable(DragEvent event) {
        double newWidth = event.getX() - image.getX();
        double newHeight = image.getNewHeightByNewWidth(newWidth);
        if (image.resizeAllowable(newWidth, newHeight)) {
            image.setNewSize(newWidth, newHeight);
        }
    }
}
