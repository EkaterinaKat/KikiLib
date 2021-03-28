package com.katyshevtseva.collage;

import com.katyshevtseva.fx.Point;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;

public abstract class Component {
    Collage collage;
    private int z;
    ImageView sizeAdjuster;
    double buttonSize;

    public int getZ() {
        return z;
    }


    /////////////////////////////   END OF API  ///////////////////////////////////////////////////

    Component(Collage collage, int z) {
        this.z = z;
        this.collage = collage;
        buttonSize = collage.getWidth() * 0.03;
        sizeAdjuster = getSizeAdjusterImageView();
    }

    void formContextMenuAndSetOnImage() {
        MenuItem bringToFrontItem = new MenuItem("Bring to front");
        bringToFrontItem.setOnAction(event -> collage.moveComponentToFirstPlanAndRefillCollage(this));

        MenuItem deleteItem = new MenuItem("Delete");
        deleteItem.setOnAction(event -> collage.deleteComponent(this));

        ContextMenu contextMenu = new ContextMenu();
        contextMenu.getItems().addAll(bringToFrontItem, deleteItem);

        setMenuOnImage(contextMenu);
    }

    private ImageView getSizeAdjusterImageView() {
        ImageView imageView = new ImageView(new Image("/resizing_arrow.png"));
        imageView.setFitWidth(buttonSize);
        imageView.setFitHeight(buttonSize);
        return imageView;
    }

    void setZ(int z) {
        this.z = z;
    }

    void updateSizeAdjusterCoordinates() {
        sizeAdjuster.setX(getX() + getWidth() - sizeAdjuster.getFitWidth() / 2);
        sizeAdjuster.setY(getY() + getHeight() - sizeAdjuster.getFitHeight() / 2);
    }

    boolean sizeAdjusterContainsPoint(Point point) {
        return ((point.getX() > sizeAdjuster.getX()) && (point.getX() < (sizeAdjuster.getX() + buttonSize))) &&
                ((point.getY() > sizeAdjuster.getY()) && (point.getY() < (sizeAdjuster.getY() + buttonSize)));
    }

    boolean imageContainsPoint(Point point) {
        return ((point.getX() > getX()) && (point.getX() < (getX() + getWidth())))
                && ((point.getY() > getY()) && (point.getY() < (getY() + getHeight())));
    }

    Point getPos() {
        return new Point(getX(), getY());
    }

    void relocateIfAllowable(Point newCoord) {
        if (relocationAllowable(newCoord))
            setCoordinates(newCoord);

    }

    private boolean relocationAllowable(Point newCoord) {
        return newCoord.getX() > 0 && newCoord.getY() > 0 &&
                newCoord.getX() + getWidth() < collage.getWidth()
                && newCoord.getY() + getHeight() < collage.getHeight();
    }

    abstract void setCoordinates(Point newCoord);

    abstract void resizeIfAllowable(double newWidth);

    abstract ImageView getImageView();

    abstract List<ImageView> getImageViewWithButtons();

    abstract void setMenuOnImage(ContextMenu menu);

    abstract double getX();

    abstract double getY();

    abstract double getWidth();

    abstract double getHeight();
}
