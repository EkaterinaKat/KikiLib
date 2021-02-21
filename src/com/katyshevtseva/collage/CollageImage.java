package com.katyshevtseva.collage;


import com.katyshevtseva.fx.Point;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CollageImage {
    private ImageView imageView;
    private ImageView sizeAdjuster;
    private int z;
    private Collage collage;
    private double sizeAdjusterSize;

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
        return imageView.getFitWidth() / collage.getWidth();
    }

    public double getRelativeHeight() {
        return imageView.getFitHeight() / collage.getHeight();
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
        imageView.setFitHeight(collage.getHeight() * relativeHeight);
        imageView.setFitWidth(collage.getWidth() * relativeWidth);
        sizeAdjuster = getSizeAdjusterImageView();
        setCoordinates(new Point(collage.getWidth() * relativeX, collage.getHeight() * relativeY));
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

    private ImageView getSizeAdjusterImageView() {
        ImageView imageView = new ImageView(new Image("/resizing_arrow.png"));
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

    void setCoordinates(Point newCoord) {
        imageView.setX(newCoord.getX());
        imageView.setY(newCoord.getY());
        setSizeAdjusterCoordinates();
    }

    private void setSizeAdjusterCoordinates() {
        sizeAdjuster.setX(imageView.getX() + imageView.getFitWidth() - sizeAdjuster.getFitWidth() / 2);
        sizeAdjuster.setY(imageView.getY() + imageView.getFitHeight() - sizeAdjuster.getFitHeight() / 2);
    }

    boolean sizeAdjusterContainsPoint(Point point) {
        return ((point.getX() > sizeAdjuster.getX()) && (point.getX() < (sizeAdjuster.getX() + sizeAdjusterSize))) &&
                ((point.getY() > sizeAdjuster.getY()) && (point.getY() < (sizeAdjuster.getY() + sizeAdjusterSize)));
    }

    boolean imageContainsPoint(Point point) {
        return ((point.getX() > imageView.getX()) && (point.getX() < (imageView.getX() + imageView.getFitWidth())))
                && ((point.getY() > imageView.getY()) && (point.getY() < (imageView.getY() + imageView.getFitHeight())));
    }

    double getX() {
        return imageView.getX();
    }

    double getY() {
        return imageView.getY();
    }

    boolean relocationAllowable(Point newCoord) {
        return newCoord.getX() + imageView.getFitWidth() < collage.getWidth()
                && newCoord.getY() + imageView.getFitHeight() < collage.getHeight();
    }

    boolean resizeAllowable(double newWidth, double newHeight) {
        return imageView.getX() + newWidth < collage.getWidth()
                && imageView.getY() + newHeight < collage.getHeight() && newWidth > collage.getWidth() * 0.1;
    }

    double getNewHeightByNewWidth(double newWidth) {
        return imageView.getFitHeight() * (newWidth / imageView.getFitWidth());
    }

    void setNewSize(double newWidth, double newHeight) {
        imageView.setFitWidth(newWidth);
        imageView.setFitHeight(newHeight);

        setSizeAdjusterCoordinates();
    }
}
