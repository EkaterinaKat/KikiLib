package com.katyshevtseva.collage.old;

class ImageParams {
    private double x;
    private double y;
    private double width;
    private double height;

    ImageParams(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    double getX() {
        return x;
    }

    double getY() {
        return y;
    }

    double getWidth() {
        return width;
    }

    double getHeight() {
        return height;
    }

    public double getRelativeWidth() {
        return imageView.getFitWidth() / collage.getWidth();
    }

    public double getRelativeHeight() {
        return imageView.getFitHeight() / collage.getHeight();
    }

    public double getRelativeX() {
        return imageView.getX() / collage.getWidth();
    }

    public double getRelativeY() {
        return imageView.getY() / collage.getHeight();
    }
}
