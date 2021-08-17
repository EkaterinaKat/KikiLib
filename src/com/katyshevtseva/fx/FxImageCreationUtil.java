package com.katyshevtseva.fx;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.MalformedURLException;

@SuppressWarnings("WeakerAccess")
public class FxImageCreationUtil {

    //Absolute path must look like this "D:\\Some_files\\wardrobe\\masik.png"
    public static ImageView getImageViewByAbsolutePath(String path) {
        return new ImageView(getImageByAbsolutePath(path));
    }

    public static Image getImageByAbsolutePath(String path) {
        return getImageByAbsolutePath(path, null, false);
    }

    public static Image getImageByAbsolutePath(String path, Double width, boolean backgroundLoading) {
        File file = new File(path);
        try {
            String localUrl = file.toURI().toURL().toString();
            if (width == null)
                return new Image(localUrl, backgroundLoading);
            return new Image(localUrl, width, 0, true, true, backgroundLoading);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        throw new RuntimeException();
    }
}
