package com.katyshevtseva.collage;

import com.katyshevtseva.fx.Point;
import javafx.scene.control.ContextMenu;
import javafx.scene.image.ImageView;

import java.util.*;

public class ImageSet extends Component {
    private List<Image> images = new ArrayList<>();
    private Image currentImage;
    private ImageView leafButton;

    public ImageSet(List<ImageView> imageViews, Collage collage) {
        super(collage, 1);
        imageViews.forEach(imageView -> {
            images.add(new Image(imageView, this.collage));
        });
        currentImage = images.get(0);
        leafButton = getLeafButtonImageView();
        setCoordinates(new Point(currentImage.getX(), currentImage.getY()));
        formContextMenuAndSetOnImage();

    }

    public ImageSet(List<Image> images, Collage collage, int z, double relativeX, double relativeY) {
        super(collage, z);
        this.images = images;
        currentImage = images.get(0);
        setCoordinates(new Point(relativeX * collage.getWidth(), relativeY * collage.getHeight()));
        formContextMenuAndSetOnImage();
        leafButton = getLeafButtonImageView();
    }

    /////////////////////////////   END OF API  ///////////////////////////////////////////////////

    private ImageView getLeafButtonImageView() {
        ImageView imageView = new ImageView(new javafx.scene.image.Image("/leaf_button.png"));
        imageView.setFitWidth(buttonSize);
        imageView.setFitHeight(buttonSize);
        return imageView;
    }

    @Override
    void setCoordinates(Point newCoord) {
        currentImage.setCoordinates(newCoord);
        updateSizeAdjusterCoordinates();
        updateLeafButtonCoordinates();
    }

    private void updateLeafButtonCoordinates() {
        leafButton.setX(getX() + getWidth() - sizeAdjuster.getFitWidth() / 2);
        leafButton.setY(getY() + getHeight() - sizeAdjuster.getFitHeight() * 3 / 2 );
    }

    @Override
    void resizeIfAllowable(double newWidth) {
        currentImage.resizeIfAllowable(newWidth);
    }

    ImageView getImageView() {
        return currentImage.getImageView();
    }

    @Override
    List<ImageView> getImageViewWithButtons() {
        return Arrays.asList(currentImage.getImageView(), sizeAdjuster, leafButton);
    }

    @Override
    void setMenuOnImage(ContextMenu menu) {
        currentImage.getImageView().setOnContextMenuRequested(e -> {
                    if (collage.isEditingMode())
                        menu.show(currentImage.getImageView(), e.getScreenX(), e.getScreenY());
                }
        );
    }

    @Override
    double getX() {
        return currentImage.getX();
    }

    @Override
    double getY() {
        return currentImage.getY();
    }

    @Override
    double getWidth() {
        return currentImage.getWidth();
    }

    @Override
    double getHeight() {
        return currentImage.getHeight();
    }
}
