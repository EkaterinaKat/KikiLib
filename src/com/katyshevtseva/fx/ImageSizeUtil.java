package com.katyshevtseva.fx;

import javafx.scene.image.ImageView;

public class ImageSizeUtil {

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