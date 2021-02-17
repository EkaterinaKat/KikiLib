package com.katyshevtseva.collage.test;

import com.katyshevtseva.collage.Collage;
import com.katyshevtseva.collage.CollageImage;
import com.katyshevtseva.fx.WindowBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.Collections;
import java.util.List;

class Controller implements WindowBuilder.FxController {
    private final int COLLAGE_HEIGHT = 800;
    private final int COLLAGE_WIDTH = 800;
    @FXML
    private Button button;
    @FXML
    private Pane pane;

    @FXML
    private void initialize() {
        ImageView imageView = new ImageView(new Image("/com/katyshevtseva/collage/test/image.png"));
        List<CollageImage> collageImages = Collections.singletonList(
                new CollageImage(imageView, 0.25, 0.25, 0.5, 0.5, COLLAGE_HEIGHT, COLLAGE_WIDTH));
        Collage collage = new Collage(collageImages, COLLAGE_HEIGHT, COLLAGE_WIDTH);
        pane.getChildren().add(collage.getCollagePane());
    }
}
