package com.katyshevtseva.collage;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;

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

    boolean reportStartOfDragEvent(double pointX, double pointY) {
        boolean sizeAdjusterContainPoint = ((pointX > sizeAdjuster.getX()) && (pointX < (sizeAdjuster.getX() + sizeAdjusterSize))) &&
                ((pointY > sizeAdjuster.getY()) && (pointY < (sizeAdjuster.getY() + sizeAdjusterSize)));
        if (sizeAdjusterContainPoint) {
            currentModificationType = RESIZING;
            return true;
        }
        boolean imageContainsPoint = ((pointX > x) && (pointX < (x + height))) && ((pointY > y) && (pointY < (y + width)));
        if (imageContainsPoint) {
            currentModificationType = MOVING;
            return true;
        }
        return false;
    }

    void reportDragEvent(DragEvent event) {
        if (currentModificationType == MOVING) {
            relocateIfAllowable(event);
        } else if (currentModificationType == RESIZING) {
            resizeIfAllowable(event);
        }
    }

    private void relocateIfAllowable(DragEvent event) {
        if (allowableRelocationEvent(event)) {
            setCoordinates(event.getX(), event.getY());
        }
    }

    private boolean allowableRelocationEvent(DragEvent event) {
        return event.getX() + height < collageHeight && event.getY() + width < collageWidth;
    }
}
