package com.katyshevtseva.collage.old;

import com.katyshevtseva.fx.Point;
import javafx.scene.control.ContextMenu;
import javafx.scene.image.ImageView;

import java.util.Arrays;
import java.util.List;

public class CollageImage extends CollageComponent {
    private ImageView imageView;

    public static CollageImage fromExistingImage(ImageView imageView, double relativeHeight, double relativeWidth,
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

    public ImageView getImageView() {
        return imageView;
    }

    /////////////////////////////   END OF API  ///////////////////////////////////////////////////

    private CollageImage(ImageView imageView, double relativeHeight, double relativeWidth,
                         double relativeX, double relativeY, Collage collage, int z) {
        super(collage, z);
        imageView.setFitHeight(collage.getHeight() * relativeHeight);
        imageView.setFitWidth(collage.getWidth() * relativeWidth);
        this.imageView = imageView;
        formContextMenuAndSetOnImage();
        setCoordinates(new Point(collage.getWidth() * relativeX, collage.getHeight() * relativeY));
    }

    void setCoordinates(Point newCoord) {
        imageView.setX(newCoord.getX());
        imageView.setY(newCoord.getY());
        setSizeAdjusterCoordinates();
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

    @Override
    List<ImageView> getImageViewWithButtons() {
        return Arrays.asList(imageView, getSizeAdjuster());
    }

    ImageParams getCurrentImageParams(){
        return new ImageParams(imageView.getX(), imageView.getY(), imageView.getFitWidth(), imageView.getFitHeight());
    }

    @Override
    void setMenuOnImage(ContextMenu contextMenu) {
        imageView.setOnContextMenuRequested(e -> {
                    if (collage.isEditingMode())
                        contextMenu.show(imageView, e.getScreenX(), e.getScreenY());
                }
        );
    }
}
