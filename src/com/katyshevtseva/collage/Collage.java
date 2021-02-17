package com.katyshevtseva.collage;

import com.katyshevtseva.fx.Styler;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;

import java.util.List;

public class Collage {
    private List<CollageImage> images;
    private int height;
    private int width;
    private Pane collagePane;
    private CollageImage changingImage;

    public Collage(List<CollageImage> images, int height, int width) {
        this.images = images;
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
        for (CollageImage image : images) {
            pane.getChildren().addAll(image.getImageView());
        }
        collagePane = pane;
    }

    public Pane getCollagePane() {
        return collagePane;
    }

    private boolean imageContainsPoint(CollageImage image, double x, double y) {
        return ((x > image.getX()) && (x < (image.getX() + image.getHeight()))) &&
                ((y > image.getY()) && (y < (image.getY() + image.getWidth())));
    }

    private void tune() {

        collagePane.setOnDragDetected(event -> {
            Dragboard db = collagePane.startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            content.putString("");
            db.setContent(content);
            event.consume();
            for (CollageImage image : images)
                if (imageContainsPoint(image, event.getX(), event.getY()))
                    changingImage = image;
        });

        collagePane.setOnDragOver(event -> {
            if (changingImage != null) {
                if (allowableRelocationEvent(event, changingImage)) {
                    changingImage.setCoordinates(event.getX(), event.getY());
                }
            }
        });

        collagePane.setOnDragDone(event -> {
            changingImage = null;
            event.consume();
        });

    }

    private boolean allowableRelocationEvent(DragEvent event, CollageImage collageImage) {
        return event.getX() + collageImage.getHeight() < height && event.getY() + collageImage.getWidth() < width;
    }
}
