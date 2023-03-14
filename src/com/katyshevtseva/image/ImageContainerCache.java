package com.katyshevtseva.image;

import com.katyshevtseva.fx.FxImageCreationUtil;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class ImageContainerCache {
    private static ImageContainerCache instance;
    private final Map<String, ImageContainer> cache = new HashMap<>();

    public static ImageContainerCache getInstance() {
        if (instance == null)
            instance = new ImageContainerCache();
        return instance;
    }

    public ImageContainer getImageContainer(String fileName, String location) {
        String fullPath = location + fileName;
        ImageContainer imageContainer = cache.get(fullPath);

        if (imageContainer != null) {
            return imageContainer;
        }

        imageContainer = new ImageContainer() {
            @Override
            public Image getImage() {
                return FxImageCreationUtil.getImageByAbsolutePath(fullPath, 400.0, false);
            }

            @Override
            public String getPath() {
                return fullPath;
            }

            @Override
            public String getFileName() {
                return fileName;
            }
        };
        cache.put(fullPath, imageContainer);

        return imageContainer;
    }
}
