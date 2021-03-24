package com.katyshevtseva.collage.old;


import com.katyshevtseva.fx.Point;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;

abstract class CollageComponent {
    Collage collage;
    private int z;
    private ImageView sizeAdjuster;
    private double sizeAdjusterSize;

    public int getZ() {
        return z;
    }

    /////////////////////////////   END OF API  ///////////////////////////////////////////////////

    CollageComponent(Collage collage, int z) {
        this.z = z;
        this.collage = collage;
        sizeAdjusterSize = collage.getWidth() * 0.03;
        sizeAdjuster = getSizeAdjusterImageView();
    }

    void formContextMenuAndSetOnImage() {
        MenuItem bringToFrontItem = new MenuItem("Bring to front");
        bringToFrontItem.setOnAction(event -> collage.moveComponentToFirstPlan(this));

        MenuItem deleteItem = new MenuItem("Delete");
        deleteItem.setOnAction(event -> collage.deleteComponent(this));

        ContextMenu contextMenu = new ContextMenu();
        contextMenu.getItems().addAll(bringToFrontItem, deleteItem);

        setMenuOnImage(contextMenu);
    }

    private ImageView getSizeAdjusterImageView() {
        ImageView imageView = new ImageView(new Image("/resizing_arrow.png"));
        imageView.setFitWidth(sizeAdjusterSize);
        imageView.setFitHeight(sizeAdjusterSize);
        return imageView;
    }

    void setZ(int z) {
        this.z = z;
    }

    ImageView getSizeAdjuster() {
        return sizeAdjuster;
    }

    void setSizeAdjusterCoordinates() {
        ImageParams params = getCurrentImageParams();
        sizeAdjuster.setX(params.getX() + params.getWidth() - sizeAdjuster.getFitWidth() / 2);
        sizeAdjuster.setY(params.getY() + params.getHeight() - sizeAdjuster.getFitHeight() / 2);
    }

    boolean sizeAdjusterContainsPoint(Point point) {
        return ((point.getX() > sizeAdjuster.getX()) && (point.getX() < (sizeAdjuster.getX() + sizeAdjusterSize))) &&
                ((point.getY() > sizeAdjuster.getY()) && (point.getY() < (sizeAdjuster.getY() + sizeAdjusterSize)));
    }

    abstract double getX();

    abstract double getY();

    abstract boolean relocationAllowable(Point newCoord);

    abstract void setCoordinates(Point newCoord);

    abstract double getNewHeightByNewWidth(double newWidth);

    abstract ImageView getImageView();

    abstract List<ImageView> getImageViewWithButtons();

    abstract boolean imageContainsPoint(Point point);

    abstract boolean resizeAllowable(double newWidth, double newHeight);

    abstract void setNewSize(double newWidth, double newHeight);

    abstract ImageParams getCurrentImageParams();

    abstract void setMenuOnImage(ContextMenu menu);
}
