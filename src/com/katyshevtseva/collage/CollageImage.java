package com.katyshevtseva.collage;


import javafx.scene.image.ImageView;

public class CollageImage {
    private ImageView imageView;
    private double height;
    private double width;
    private double x;
    private double y;
    private double collageHeight;
    private double collageWidth;

    public CollageImage(ImageView imageView, double relativeHeight, double relativeWidth,
                        double relativeX, double relativeY, double collageHeight, double collageWidth) {
        this.collageHeight = collageHeight;
        this.collageWidth = collageWidth;
        this.imageView = imageView;
        height = collageHeight * relativeHeight;
        width = collageWidth * relativeWidth;
        setCoordinates(collageWidth * relativeX, collageHeight * relativeY);
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);
    }

    ImageView getImageView() {
        return imageView;
    }

    double getHeight() {
        return height;
    }

    double getWidth() {
        return width;
    }

    double getX() {
        return x;
    }

    double getY() {
        return y;
    }

    void setCoordinates(double x, double y) {
        imageView.setX(x);
        this.x = x;
        imageView.setY(y);
        this.y = y;
    }
}
