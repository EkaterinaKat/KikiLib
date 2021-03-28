package com.katyshevtseva.collage;

import com.katyshevtseva.fx.Point;
import javafx.scene.control.ContextMenu;
import javafx.scene.image.ImageView;

import java.util.Arrays;
import java.util.List;

public class Image extends Component {
    private ImageView imageView;

    public Image(ImageView imageView, Collage collage, int z, double relativeX, double relativeY, double relativeWidth, double relativeHeight) {
        super(collage, z);
        this.imageView = imageView;
        setImageViewInitParams(new ImageParams(relativeX, relativeY, relativeWidth, relativeHeight));
        formContextMenuAndSetOnImage();
    }

    public Image(ImageView imageView, Collage collage) {
        super(collage, 1);
        this.imageView = imageView;
        setImageViewInitParams(new ImageParams());
        formContextMenuAndSetOnImage();
    }

    /////////////////////////////   END OF API  ///////////////////////////////////////////////////

    private void setImageViewInitParams(ImageParams params) {
        imageView.setFitHeight(params.initHeight);
        imageView.setFitWidth(params.initWidth);
        setCoordinates(new Point(params.initX, params.initY));
    }

    void setCoordinates(Point newCoord) {
        imageView.setX(newCoord.getX());
        imageView.setY(newCoord.getY());
        updateSizeAdjusterCoordinates();
    }

    @Override
    void resizeIfAllowable(double newWidth) {
        double newHeight = imageView.getFitHeight() * (newWidth / imageView.getFitWidth());

        if (resizeAllowable(newWidth, newHeight))
            setNewSize(newWidth, newHeight);
    }

    private boolean resizeAllowable(double newWidth, double newHeight) {
        return imageView.getX() + newWidth < collage.getWidth()
                && imageView.getY() + newHeight < collage.getHeight() && newWidth > collage.getWidth() * 0.1;
    }

    private void setNewSize(double newWidth, double newHeight) {
        imageView.setFitWidth(newWidth);
        imageView.setFitHeight(newHeight);
        updateSizeAdjusterCoordinates();
    }

    @Override
    List<ImageView> getImageViewWithButtons() {
        return Arrays.asList(imageView, sizeAdjuster);
    }

    @Override
    void setMenuOnImage(ContextMenu menu) {
        imageView.setOnContextMenuRequested(e -> {
                    if (collage.isEditingMode())
                        menu.show(imageView, e.getScreenX(), e.getScreenY());
                }
        );
    }

    @Override
    double getX() {
        return imageView.getX();
    }

    @Override
    double getY() {
        return imageView.getY();
    }

    @Override
    double getWidth() {
        return imageView.getFitWidth();
    }

    @Override
    double getHeight() {
        return imageView.getFitHeight();
    }

    ImageView getImageView() {
        return imageView;
    }


    class ImageParams {
        private double initX;
        private double initY;
        private double initWidth;
        private double initHeight;

        ImageParams(){
            double relativeWidth = 0.3;
            double relativeHeight = (relativeWidth * imageView.getImage().getHeight()) / imageView.getImage().getWidth();
            double relativeX = 0.5 - relativeWidth / 2.0;
            double relativeY = 0.5 - relativeHeight / 2.0;
            calculateAbsoluteInitialValues(relativeX, relativeY, relativeWidth, relativeHeight);
        }

        ImageParams(double relativeX, double relativeY, double relativeWidth, double relativeHeight) {
            calculateAbsoluteInitialValues(relativeX, relativeY, relativeWidth, relativeHeight);
        }

        private void calculateAbsoluteInitialValues(double relativeX, double relativeY, double relativeWidth, double relativeHeight) {
            initX = collage.getWidth() * relativeX;
            initY = collage.getHeight() * relativeY;
            initHeight = collage.getHeight() * relativeHeight;
            initWidth = collage.getWidth() * relativeWidth;
        }
    }
}
