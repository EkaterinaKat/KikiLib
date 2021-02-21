package com.katyshevtseva.collage;


import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
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
    private int z;
    private Collage collage;
    private double sizeAdjusterSize;
    private CurrentModificationType currentModificationType;
    private double dragCursorStartX;
    private double dragCursorStartY;
    private double dragImageStartX;
    private double dragImageStartY;

    public static CollageImage formExistingImage(ImageView imageView, double relativeHeight, double relativeWidth,
                                                 double relativeX, double relativeY, Collage collage, int z) {
        return new CollageImage(imageView, relativeHeight, relativeWidth, relativeX, relativeY, collage, z);
    }

    public static CollageImage createNewImage(ImageView imageView, Collage collage) {
        double initRelativeWidth = 0.3;
        double initRelativeHeight = (initRelativeWidth * imageView.getImage().getHeight()) / imageView.getImage().getWidth();
        double initRelativeX = 0.5 - initRelativeWidth / 2.0;
        double initRelativeY = 0.5 - initRelativeHeight / 2.0;
        return new CollageImage(imageView, initRelativeHeight, initRelativeWidth, initRelativeX, initRelativeY, collage, 1);
    }

    public double getRelativeWidth() {
        return width / collage.getWidth();
    }

    public double getRelativeHeight() {
        return height / collage.getHeight();
    }

    public double getRelativeX() {
        return imageView.getX() / collage.getWidth();
    }

    public double getRelativeY() {
        return imageView.getY() / collage.getHeight();
    }

    public ImageView getImageView() {
        return imageView;
    }

    public int getZ() {
        return z;
    }

    /////////////////////////////   END OF API  ///////////////////////////////////////////////////

    private CollageImage(ImageView imageView, double relativeHeight, double relativeWidth,
                         double relativeX, double relativeY, Collage collage, int z) {
        this.collage = collage;
        this.z = z;
        sizeAdjusterSize = collage.getWidth() * 0.03;
        this.imageView = imageView;
        height = collage.getHeight() * relativeHeight;
        width = collage.getWidth() * relativeWidth;
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);
        sizeAdjuster = getSizeAdjusterImageView();
        setCoordinates(collage.getWidth() * relativeX, collage.getHeight() * relativeY);
        adjustContextMenu();
    }

    private void adjustContextMenu() {
        MenuItem bringToFrontItem = new MenuItem("Bring to front");
        bringToFrontItem.setOnAction(event -> collage.moveImageToFirstPlan(this));

        MenuItem deleteItem = new MenuItem("Delete");
        deleteItem.setOnAction(event -> collage.deleteImage(this));

        ContextMenu contextMenu = new ContextMenu();
        contextMenu.getItems().addAll(bringToFrontItem, deleteItem);
        imageView.setOnContextMenuRequested(e -> {
                    if (collage.isEditingMode())
                        contextMenu.show(imageView, e.getScreenX(), e.getScreenY());
                }
        );
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

    ImageView getSizeAdjuster() {
        return sizeAdjuster;
    }

    void setZ(int z) {
        this.z = z;
    }

    private void setCoordinates(double x, double y) {
        imageView.setX(x);
        imageView.setY(y);
        setSizeAdjusterCoordinates();
    }

    private void setSizeAdjusterCoordinates() {
        sizeAdjuster.setX(imageView.getX() + width - sizeAdjuster.getFitWidth() / 2);
        sizeAdjuster.setY(imageView.getY() + height - sizeAdjuster.getFitHeight() / 2);
    }

    private void resizeIfAllowable(DragEvent event) {
        double newWidth = event.getX() - imageView.getX();
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
        return imageView.getX() + newWidth < collage.getWidth()
                && imageView.getY() + newHeight < collage.getHeight() && newWidth > collage.getWidth() * 0.1;
    }

    boolean reportStartOfDragEvent(MouseEvent event) {
        boolean sizeAdjusterContainPoint = ((event.getX() > sizeAdjuster.getX()) && (event.getX() < (sizeAdjuster.getX() + sizeAdjusterSize))) &&
                ((event.getY() > sizeAdjuster.getY()) && (event.getY() < (sizeAdjuster.getY() + sizeAdjusterSize)));
        if (sizeAdjusterContainPoint) {
            currentModificationType = RESIZING;
            saveDragStartCoordinates(event.getX(), event.getY());
            return true;
        }
        boolean imageContainsPoint = ((event.getX() > imageView.getX()) && (event.getX() < (imageView.getX() + width)))
                && ((event.getY() > imageView.getY()) && (event.getY() < (imageView.getY() + height)));
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
        dragImageStartX = imageView.getX();
        dragImageStartY = imageView.getY();
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
        return newX + width < collage.getWidth() && newY + height < collage.getHeight();
    }
}
