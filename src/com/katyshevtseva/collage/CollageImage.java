package com.katyshevtseva.collage;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;

import static com.katyshevtseva.collage.CollageImage.CurrentModificationType.MOVING;
import static com.katyshevtseva.collage.CollageImage.CurrentModificationType.RESIZING;

public class CollageImage {
    private ImageView imageView;
    private ImageView sizeAdjuster;
    private double height;
    private double width;
    private double x;
    private double y;
    private int z;
    private double collageHeight;
    private double collageWidth;
    private double sizeAdjusterSize;
    private CurrentModificationType currentModificationType;
    private double dragCursorStartX;
    private double dragCursorStartY;
    private double dragImageStartX;
    private double dragImageStartY;

    public CollageImage(ImageView imageView, double relativeHeight, double relativeWidth,
                        double relativeX, double relativeY, double collageHeight, double collageWidth, int z) {
        this.z = z;
        sizeAdjusterSize = collageWidth * 0.03;
        this.collageHeight = collageHeight;
        this.collageWidth = collageWidth;
        this.imageView = imageView;
        height = collageHeight * relativeHeight;
        width = collageWidth * relativeWidth;
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);
        sizeAdjuster = getSizeAdjusterImageView();
        setCoordinates(collageWidth * relativeX, collageHeight * relativeY);
    }

    enum CurrentModificationType {
        RESIZING, MOVING
    }

    private ImageView getSizeAdjusterImageView() {
        ImageView imageView = new ImageView(new Image("/arrow.png"));
        imageView.setFitWidth(sizeAdjusterSize);
        imageView.setFitHeight(sizeAdjusterSize);
        return imageView;
    }

    ImageView getImageView() {
        return imageView;
    }

    ImageView getSizeAdjuster() {
        return sizeAdjuster;
    }

    int getZ() {
        return z;
    }

    void setZ(int z) {
        this.z = z;
    }

    private void setCoordinates(double x, double y) {
        imageView.setX(x);
        this.x = x;
        imageView.setY(y);
        this.y = y;
        setSizeAdjusterCoordinates();
    }

    private void setSizeAdjusterCoordinates() {
        sizeAdjuster.setX(x + width - sizeAdjuster.getFitWidth() / 2);
        sizeAdjuster.setY(y + height - sizeAdjuster.getFitHeight() / 2);
    }

    private void resizeIfAllowable(DragEvent event) {
        double newWidth = event.getX() - x;
        double newHeight = height * (newWidth / width);
        if (resizeAllowable(newWidth, newHeight)) {
            imageView.setFitWidth(newWidth);
            imageView.setFitHeight(newHeight);
            height = newHeight;
            width = newWidth;
        }
        setSizeAdjusterCoordinates();
    }

    private boolean resizeAllowable(double newWidth, double newHeight) {
        return x + newWidth < collageWidth && y + newHeight < collageHeight && newWidth > collageWidth * 0.1;
    }

    boolean reportStartOfDragEvent(MouseEvent event) {
        boolean sizeAdjusterContainPoint = ((event.getX() > sizeAdjuster.getX()) && (event.getX() < (sizeAdjuster.getX() + sizeAdjusterSize))) &&
                ((event.getY() > sizeAdjuster.getY()) && (event.getY() < (sizeAdjuster.getY() + sizeAdjusterSize)));
        if (sizeAdjusterContainPoint) {
            currentModificationType = RESIZING;
            saveDragStartCoordinates(event.getX(), event.getY());
            return true;
        }
        boolean imageContainsPoint = ((event.getX() > x) && (event.getX() < (x + height))) && ((event.getY() > y) && (event.getY() < (y + width)));
        if (imageContainsPoint) {
            currentModificationType = MOVING;
            saveDragStartCoordinates(event.getX(), event.getY());
            return true;
        }
        return false;
    }

    private void saveDragStartCoordinates(double cursorX, double cursorY) {
        dragCursorStartX = cursorX;
        dragCursorStartY = cursorY;
        dragImageStartX = this.x;
        dragImageStartY = this.y;
    }

    void reportDragEvent(DragEvent event) {
        if (currentModificationType == MOVING) {
            relocateIfAllowable(event);
        } else if (currentModificationType == RESIZING) {
            resizeIfAllowable(event);
        }
    }

    private void relocateIfAllowable(DragEvent event) {
        double newX = event.getX() - dragCursorStartX + dragImageStartX;
        double newY = event.getY() - dragCursorStartY + dragImageStartY;
        if (newX > 0 && newY > 0 && allowableRelocationEvent(newX, newY)) {
            setCoordinates(newX, newY);
        }
    }

    private boolean allowableRelocationEvent(double newX, double newY) {
        return newX + height < collageHeight && newY + width < collageWidth;
    }
}
