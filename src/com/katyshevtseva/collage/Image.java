package com.katyshevtseva.collage;

import com.katyshevtseva.fx.Point;
import javafx.scene.control.ContextMenu;
import javafx.scene.image.ImageView;

import java.util.Arrays;
import java.util.List;

public class Image extends Component {
    private ImageView imageView;

    public static Image fromExistingImage(ImageView imageView, ImageParams params, Collage collage, int z) {
        return new Image(imageView, params, collage, z);
    }

    public static Image createNewImage(ImageView imageView, Collage collage) {
        ImageParams params = ImageParams.getParams(collage, imageView);
        return new Image(imageView, params, collage, 1);
    }

    public ImageView getImageView() {
        return imageView;
    }

    /////////////////////////////   END OF API  ///////////////////////////////////////////////////

    private Image(ImageView imageView, ImageParams params, Collage collage, int z) {
        super(collage, params, z);
        this.imageView = imageView;
        imageView.setFitHeight(params.getInitHeight());
        imageView.setFitWidth(params.getInitWidth());
        formContextMenuAndSetOnImage();
        setCoordinates(new Point(params.getInitX(), params.getInitY()));
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
}
