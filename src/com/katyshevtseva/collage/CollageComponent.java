package com.katyshevtseva.collage;


import com.katyshevtseva.fx.Point;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;

import java.util.List;

abstract class CollageComponent {
    Collage collage;

    CollageComponent(ImageView imageView) {
        initializeImageView(imageView);
        adjustContextMenu();
    }

    private void adjustContextMenu() {
        MenuItem bringToFrontItem = new MenuItem("Bring to front");
        bringToFrontItem.setOnAction(event -> collage.moveComponentToFirstPlan(this));

        MenuItem deleteItem = new MenuItem("Delete");
        deleteItem.setOnAction(event -> collage.deleteComponent(this));

        ContextMenu contextMenu = new ContextMenu();
        contextMenu.getItems().addAll(bringToFrontItem, deleteItem);
        getImageView().setOnContextMenuRequested(e -> {  //todo тут должно быть что-то по типу getNode
                    if (collage.isEditingMode())
                        contextMenu.show(getImageView(), e.getScreenX(), e.getScreenY());
                }
        );
    }


    abstract double getX();

    abstract double getY();

    abstract boolean relocationAllowable(Point newCoord);

    abstract void setCoordinates(Point newCoord);

    abstract double getNewHeightByNewWidth(double newWidth);

    abstract int getZ();

    abstract ImageView getImageView();

    abstract List<ImageView> getImageViewWithButtons();

    abstract boolean imageContainsPoint(Point point);

    abstract boolean sizeAdjusterContainsPoint(Point point);

    abstract boolean resizeAllowable(double newWidth, double newHeight);

    abstract void setNewSize(double newWidth, double newHeight);

    abstract void setZ(int z);

    abstract void initializeImageView(ImageView imageView);
}
