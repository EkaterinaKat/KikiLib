package com.katyshevtseva.fx;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.MalformedURLException;

@SuppressWarnings("WeakerAccess")
public class ImageUtils {

    public static ImageView getImageViewByAbsolutePath(String path) {
        return new ImageView(getImageByAbsolutePath(path));
    }

    //Absolute path must look like this "D:\\Some_files\\wardrobe\\masik.png"
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
