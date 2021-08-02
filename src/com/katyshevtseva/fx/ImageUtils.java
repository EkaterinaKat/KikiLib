package com.katyshevtseva.fx;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.MalformedURLException;

public class ImageUtils {

    public static ImageView getImageViewByAbsolutePath(String path) {
        return new ImageView(getImageByAbsolutePath(path));
    }

    public static Image getImageByAbsolutePath(String path) {
        File file = new File(path);
        try {
            String localUrl = file.toURI().toURL().toString();
            return new Image(localUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        throw new RuntimeException();
    }
}
