package com.katyshevtseva.collage;

import com.katyshevtseva.fx.Styler;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;

import java.util.Comparator;
import java.util.List;

public class Collage {
    private List<CollageImage> collageImages;
    private int height;
    private int width;
    private Pane collagePane;
    private CollageImage changingImage;

    public Collage(List<CollageImage> collageImages, int height, int width) {
        this.collageImages = collageImages;
        this.height = height;
        this.width = width;
        initializeCollagePane();
        tune();
    }

    private void initializeCollagePane() {
        Pane pane = new Pane();
        pane.setMaxWidth(width);
        pane.setMinWidth(width);
        pane.setMaxHeight(height);
        pane.setMinHeight(height);
        pane.setStyle(Styler.getColorfullStyle(Styler.ThingToColor.BACKGROUND, "#023648"));
        collageImages.sort(Comparator.comparing(CollageImage::getZ));
        for (CollageImage image : collageImages) {
            pane.getChildren().addAll(image.getImageView(), image.getSizeAdjuster());
        }
        collagePane = pane;
    }

    public Pane getCollagePane() {
        return collagePane;
    }

    private void tune() {

        collagePane.setOnDragDetected(event -> {
            Dragboard db = collagePane.startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            content.putString("");
            db.setContent(content);
            event.consume();
            for (CollageImage image : collageImages)
                if (image.reportStartOfDragEvent(event)) {
                    changingImage = image;
                    moveImageToFirstPlan(changingImage);
                    break;
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

    private void moveImageToFirstPlan(CollageImage collageImage) {
        collagePane.getChildren().removeAll(collageImage.getImageView(), collageImage.getSizeAdjuster());
        collagePane.getChildren().addAll(collageImage.getImageView(), collageImage.getSizeAdjuster());
        collageImage.setZ(getImagesMaxZ() + 1);
        collageImages.sort(Comparator.comparing(CollageImage::getZ).reversed());
    }

    private int getImagesMaxZ() {
        int result = 0;
        for (CollageImage collageImage : collageImages)
            if (collageImage.getZ() > result)
                result = collageImage.getZ();
        return result;
    }
}
