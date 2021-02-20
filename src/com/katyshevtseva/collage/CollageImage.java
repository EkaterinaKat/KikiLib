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
    private double collageHeight;
    private double collageWidth;
    private double sizeAdjusterSize;
    private CurrentModificationType currentModificationType;

    public CollageImage(ImageView imageView, double relativeHeight, double relativeWidth,
                        double relativeX, double relativeY, double collageHeight, double collageWidth) {
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

    double getHeight() {
        return height;
    }

    double getWidth() {
        return width;
    }

    double getX() {
        return x;
    }

    double getY() {
        return y;
    }

    ImageView getSizeAdjuster() {
        return sizeAdjuster;
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

    private void resize(DragEvent event) {
        double newWidth = event.getX() - x;
        double newHeight = height * (newWidth / width);
        System.out.println("height = " + height);
        System.out.println("width = " + width);
        System.out.println("newWidth = " + newWidth);
        System.out.println("newHeight = " + newHeight);
        if (resizeAllowable(newWidth, newHeight)) {
            System.out.println("allowable");
            imageView.setFitWidth(newWidth);
            imageView.setFitHeight(newHeight);
            height = newHeight;
            width = newWidth;
        } else
            System.out.println("not allowable");
        setSizeAdjusterCoordinates();
    }

    private boolean resizeAllowable(double newWidth, double newHeight) {
        System.out.println("x = " + x);
        System.out.println("newWidth = " + newWidth);
        System.out.println("collageWidth = " + collageWidth);
        System.out.println("y = " + y);
        System.out.println("newHeight = " + newHeight);
        System.out.println("collageHeight = " + collageHeight);
        System.out.println("x + newWidth < collageWidth = " + (x + newWidth < collageWidth));
        System.out.println("y + newHeight < collageHeight = " + (y + newHeight < collageHeight));
        return x + newWidth < collageWidth && y + newHeight < collageHeight && newWidth > collageWidth * 0.1;
    }

    private boolean allowableRelocationEvent(DragEvent event) {
        return event.getX() + height < collageHeight && event.getY() + width < collageWidth;
    }

    boolean containsPoint(double pointX, double pointY) {  //todo некорректное название
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
            System.out.println("moving");
            if (allowableRelocationEvent(event)) {
                System.out.println("allowable");
                setCoordinates(event.getX(), event.getY());
            }
        } else if (currentModificationType == RESIZING) {
            System.out.println("resize");
            resize(event);
        }
    }
}
