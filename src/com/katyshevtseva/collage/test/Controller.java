package com.katyshevtseva.collage.test;

import com.katyshevtseva.collage.Collage;
import com.katyshevtseva.collage.CollageImage;
import com.katyshevtseva.fx.WindowBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
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
        List<CollageImage> collageImages = new ArrayList<>();

        ImageView imageView1 = new ImageView(new Image("/com/katyshevtseva/collage/test/image1.png"));
        collageImages.add(new CollageImage(imageView1, 0.25, 0.25, 0.5, 0.5, COLLAGE_HEIGHT, COLLAGE_WIDTH));
        ImageView imageView2 = new ImageView(new Image("/com/katyshevtseva/collage/test/image2.png"));
        collageImages.add(new CollageImage(imageView2, 0.25, 0.25, 0, 0, COLLAGE_HEIGHT, COLLAGE_WIDTH));
        ImageView imageView3 = new ImageView(new Image("/com/katyshevtseva/collage/test/image3.png"));
        collageImages.add(new CollageImage(imageView3, 0.25, 0.25, 0.7, 0.3, COLLAGE_HEIGHT, COLLAGE_WIDTH));
        ImageView imageView4 = new ImageView(new Image("/com/katyshevtseva/collage/test/image4.png"));
        collageImages.add(new CollageImage(imageView4, 0.25, 0.25, 0.3, 0.7, COLLAGE_HEIGHT, COLLAGE_WIDTH));
        ImageView imageView5 = new ImageView(new Image("/com/katyshevtseva/collage/test/image5.png"));
        collageImages.add(new CollageImage(imageView5, 0.25, 0.25, 0.1, 0.2, COLLAGE_HEIGHT, COLLAGE_WIDTH));

        Collage collage = new Collage(collageImages, COLLAGE_HEIGHT, COLLAGE_WIDTH);
        pane.getChildren().add(collage.getCollagePane());
    }
}
