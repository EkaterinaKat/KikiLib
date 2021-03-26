package com.katyshevtseva.collage.test;

import com.katyshevtseva.collage.Collage;
import com.katyshevtseva.fx.WindowBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.ArrayDeque;
import java.util.Deque;

class Controller implements WindowBuilder.FxController {
    private Deque<String> imageUrls;
    @FXML
    private Button button;
    @FXML
    private Pane pane;
    @FXML
    private Button editingModeOn;
    @FXML
    private Button editingModeOff;

    {
        imageUrls = new ArrayDeque<>();
        imageUrls.addFirst("/com/katyshevtseva/collage/test/image1.png");
        imageUrls.addFirst("/com/katyshevtseva/collage/test/image2.png");
        imageUrls.addFirst("/com/katyshevtseva/collage/test/image3.png");
        imageUrls.addFirst("/com/katyshevtseva/collage/test/image4.png");
        imageUrls.addFirst("/com/katyshevtseva/collage/test/image5.png");
        imageUrls.addFirst("/com/katyshevtseva/collage/test/image6.jpg");
        imageUrls.addFirst("/com/katyshevtseva/collage/test/image7.jpg");
    }

    @FXML
    private void initialize() {
        Collage collage = new Collage(800, 800);

        // Тестируем вторую статическую фабрику
        button.setOnAction(event -> {
            String imageUrl = imageUrls.pollFirst();
            if (imageUrl != null) {
                ImageView imageView = new ImageView(new Image(imageUrl));
                collage.addComponent(com.katyshevtseva.collage.Image.createNewImage(imageView, collage));
            }
        });

        editingModeOn.setOnAction(event -> collage.setEditingMode(true));
        editingModeOff.setOnAction(event -> collage.setEditingMode(false));

        pane.getChildren().add(collage.getCollagePane());
    }

    private void old() {
        // Тестируем  первую статическую фабрику
//        ImageView imageView1 = new ImageView(new Image(imageUrls.pollFirst()));
//        collageImages.add(CollageImage.formExistingImage(imageView1, 0.25, 0.25, 0.5, 0.5, collage, 1));
//        ImageView imageView2 = new ImageView(new Image(imageUrls.pollFirst()));
//        collageImages.add(CollageImage.formExistingImage(imageView2, 0.25, 0.25, 0, 0, collage, 2));
//        ImageView imageView3 = new ImageView(new Image(imageUrls.pollFirst()));
//        collageImages.add(CollageImage.formExistingImage(imageView3, 0.25, 0.25, 0.7, 0.3, collage, 3));
//        ImageView imageView4 = new ImageView(new Image(imageUrls.pollFirst()));
//        collageImages.add(CollageImage.formExistingImage(imageView4, 0.25, 0.25, 0.3, 0.7, collage, 4));
//        ImageView imageView5 = new ImageView(new Image(imageUrls.pollFirst()));
//        collageImages.add(CollageImage.formExistingImage(imageView5, 0.25, 0.25, 0.1, 0.2, collage, 5));
    }
}
