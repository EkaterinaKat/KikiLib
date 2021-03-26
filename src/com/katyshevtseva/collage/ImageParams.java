package com.katyshevtseva.collage;

import javafx.scene.image.ImageView;

class ImageParams extends ComponentParams {
    private Collage collage;
    private ImageView imageView;
    private double initX;
    private double initY;
    private double initWidth;
    private double initHeight;
    private double relativeX;
    private double relativeY;
    private double relativeWidth;
    private double relativeHeight;

    static ImageParams getParams(Collage collage, ImageView imageView,
                                 double relativeX, double relativeY, double relativeWidth, double relativeHeight) {
        return new ImageParams(collage, imageView, relativeX, relativeY, relativeWidth, relativeHeight);
    }

    static ImageParams getParams(Collage collage, ImageView imageView) {
        double relativeWidth = 0.3;
        double relativeHeight = (relativeWidth * imageView.getImage().getHeight()) / imageView.getImage().getWidth();
        double relativeX = 0.5 - relativeWidth / 2.0;
        double relativeY = 0.5 - relativeHeight / 2.0;
        return new ImageParams(collage, imageView, relativeX, relativeY, relativeWidth, relativeHeight);
    }

    private ImageParams(Collage collage, ImageView imageView,
                        double relativeX, double relativeY, double relativeWidth, double relativeHeight) {
        this.collage = collage;
        this.imageView = imageView;
        this.relativeX = relativeX;
        this.relativeY = relativeY;
        this.relativeWidth = relativeWidth;
        this.relativeHeight = relativeHeight;
        calculateAbsoluteInitialValues();
    }

    private void calculateAbsoluteInitialValues() {
        initX = collage.getWidth() * relativeX;
        initY = collage.getHeight() * relativeY;
        initHeight = collage.getHeight() * relativeHeight;
        initWidth = collage.getWidth() * relativeWidth;
    }

    @Override
    double getX() {
        return imageView.getX();
    }

    @Override
    double getY() {
        return imageView.getY();
    }

    @Override
    double getHeight() {
        return imageView.getFitHeight();
    }

    @Override
    double getWidth() {
        return imageView.getFitWidth();
    }

    double getInitX() {
        return initX;
    }

    double getInitY() {
        return initY;
    }

    double getInitWidth() {
        return initWidth;
    }

    double getInitHeight() {
        return initHeight;
    }
}
