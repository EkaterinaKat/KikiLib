package com.katyshevtseva.collage;

import com.katyshevtseva.fx.Styler;
import javafx.scene.input.ClipboardContent;
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
            for (CollageImage image : images)
                if (image.containsPoint(event.getX(), event.getY())) {
                    System.out.println("imageContainsPoint");
                    changingImage = image;
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
}
