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
    private double sizeAdjusterSize;
    private ComponentParams params;

    public int getZ() {
        return z;
    }


    /////////////////////////////   END OF API  ///////////////////////////////////////////////////

    Component(Collage collage, ComponentParams params, int z) {
        this.z = z;
        this.collage = collage;
        this.params = params;
        sizeAdjusterSize = collage.getWidth() * 0.03;
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
        imageView.setFitWidth(sizeAdjusterSize);
        imageView.setFitHeight(sizeAdjusterSize);
        return imageView;
    }

    void setZ(int z) {
        this.z = z;
    }

    void updateSizeAdjusterCoordinates() {
        sizeAdjuster.setX(params.getX() + params.getWidth() - sizeAdjuster.getFitWidth() / 2);
        sizeAdjuster.setY(params.getY() + params.getHeight() - sizeAdjuster.getFitHeight() / 2);
    }

    boolean sizeAdjusterContainsPoint(Point point) {
        return ((point.getX() > sizeAdjuster.getX()) && (point.getX() < (sizeAdjuster.getX() + sizeAdjusterSize))) &&
                ((point.getY() > sizeAdjuster.getY()) && (point.getY() < (sizeAdjuster.getY() + sizeAdjusterSize)));
    }

    boolean imageContainsPoint(Point point) {
        return ((point.getX() > params.getX()) && (point.getX() < (params.getX() + params.getWidth())))
                && ((point.getY() > params.getY()) && (point.getY() < (params.getY() + params.getHeight())));
    }

    Point getPos() {
        return new Point(params.getX(), params.getY());
    }

    void relocateIfAllowable(Point newCoord) {
        if (relocationAllowable(newCoord))
            setCoordinates(newCoord);

    }

    private boolean relocationAllowable(Point newCoord) {
        return newCoord.getX() > 0 && newCoord.getY() > 0 &&
                newCoord.getX() + params.getWidth() < collage.getWidth()
                && newCoord.getY() + params.getHeight() < collage.getHeight();
    }

    abstract void setCoordinates(Point newCoord);

    abstract void resizeIfAllowable(double newWidth);

    abstract ImageView getImageView();

    abstract List<ImageView> getImageViewWithButtons();

    abstract void setMenuOnImage(ContextMenu menu);
}
