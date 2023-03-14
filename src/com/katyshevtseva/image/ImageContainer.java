package com.katyshevtseva.image;

import javafx.scene.image.Image;

/**
 * Must contain image absolute path
 * Absolute path must look like this "D:\\Some_files\\wardrobe\\masik.png"
 */
public interface ImageContainer {
    Image getImage();

    String getPath();

    String getFileName();
}
