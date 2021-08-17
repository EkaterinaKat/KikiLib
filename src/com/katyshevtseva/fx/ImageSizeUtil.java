package com.katyshevtseva.fx;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class ImageSizeUtil {

    public static Node placeImageInSquare(ImageView imageView, int squareSide) {

        new BackgroundLoadedImageAdjuster(imageView.getImage(), () -> {
            if (getHeightByWidth(imageView, squareSide) <= squareSide) {
                imageView.setFitWidth(squareSide);
                imageView.setFitHeight(getHeightByWidth(imageView, squareSide));
            } else {
                imageView.setFitWidth(getWidthByHeight(imageView, squareSide));
                imageView.setFitHeight(squareSide);
            }
        }).start();

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(imageView);
        BorderPane.setAlignment(imageView, Pos.CENTER);
        borderPane.setMaxHeight(squareSide);
        borderPane.setMaxWidth(squareSide);
        borderPane.setMinHeight(squareSide);
        borderPane.setMinWidth(squareSide);

        return borderPane;
    }


    public static double getHeightByWidth(ImageView imageView, double width) {
        return getHeightToWidthRatio(imageView) * width;
    }

    public static double getWidthByHeight(ImageView imageView, double height) {
        return height / getHeightToWidthRatio(imageView);
    }

    private static double getHeightToWidthRatio(ImageView imageView) {
        return imageView.getImage().getHeight() / imageView.getImage().getWidth();
    }
}