package com.katyshevtseva.collage;

import com.katyshevtseva.fx.Styler;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Collage {
    private List<CollageImage> images = new ArrayList<>();
    private int height;
    private int width;
    private Pane collagePane;
    private CollageImage changingImage;
    private boolean editingMode;

    public Collage(int height, int width, boolean editingMode) {
        this.editingMode = editingMode;
        this.height = height;
        this.width = width;
        initializeCollagePane();
        tune();
    }

    public Pane getCollagePane() {
        return collagePane;
    }

    public void setImages(List<CollageImage> collageImages) {
        this.images = collageImages;
        addImagesOnPane();
    }

    public void addImage(CollageImage collageImage) {
        images.add(collageImage);
        moveImageToFirstPlan(collageImage);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void setEditingMode(boolean editingMode) {
        this.editingMode = editingMode;
        addImagesOnPane();
    }

    /////////////////////////////   END OF API  ///////////////////////////////////////////////////

    private void initializeCollagePane() {
        Pane pane = new Pane();
        pane.setMaxWidth(width);
        pane.setMinWidth(width);
        pane.setMaxHeight(height);
        pane.setMinHeight(height);
        pane.setStyle(Styler.getColorfullStyle(Styler.ThingToColor.BACKGROUND, "#023648"));
        collagePane = pane;
    }

    private void addImagesOnPane() {
        collagePane.getChildren().clear();
        images.sort(Comparator.comparing(CollageImage::getZ));
        for (CollageImage image : images) {
            if (editingMode)
                collagePane.getChildren().addAll(image.getImageView(), image.getSizeAdjuster());
            else
                collagePane.getChildren().add(image.getImageView());
        }
    }

    private void tune() {

        collagePane.setOnDragDetected(event -> {
            if (editingMode) {
                Dragboard db = collagePane.startDragAndDrop(TransferMode.ANY);
                ClipboardContent content = new ClipboardContent();
                content.putString("");
                db.setContent(content);
                event.consume();
                for (CollageImage image : images)
                    if (image.reportStartOfDragEvent(event)) {
                        changingImage = image;
                        moveImageToFirstPlan(changingImage);
                        break;
                    }
            }
        });

        collagePane.setOnDragOver(event -> {
            if (changingImage != null) {
                changingImage.reportDragEvent(event);
            }
        });

        collagePane.setOnDragDone(event -> {
            changingImage = null;
            event.consume();
        });

    }

    void moveImageToFirstPlan(CollageImage collageImage) {
        collagePane.getChildren().removeAll(collageImage.getImageView(), collageImage.getSizeAdjuster());
        collagePane.getChildren().addAll(collageImage.getImageView(), collageImage.getSizeAdjuster());
        collageImage.setZ(getImagesMaxZ() + 1);
        normalizeZCoordinates();
        images.sort(Comparator.comparing(CollageImage::getZ).reversed());
    }

    void deleteImage(CollageImage collageImage) {
        images.remove(collageImage);
        collagePane.getChildren().removeAll(collageImage.getImageView(), collageImage.getSizeAdjuster());
    }

    private int getImagesMaxZ() {
        int result = 0;
        for (CollageImage collageImage : images)
            if (collageImage.getZ() > result)
                result = collageImage.getZ();
        return result;
    }

    private void normalizeZCoordinates() {
        images.sort(Comparator.comparing(CollageImage::getZ));
        for (int i = 0; i < images.size(); i++) {
            images.get(i).setZ(i + 1);
        }
    }

    boolean isEditingMode() {
        return editingMode;
    }
}
