package com.katyshevtseva.fx;

import com.katyshevtseva.general.Cache;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.MalformedURLException;

@SuppressWarnings("WeakerAccess")
public class FxImageCreationUtil {
    private static final String ICONS_LOCATION = "D:\\onedrive\\central_image_storage\\icons\\";
    private static final Cache<IconPicture, Image> iconCache = new Cache<>(FxImageCreationUtil::getIconByPicture);

    public static Image getIcon(IconPicture iconPicture) {
        return iconCache.getValue(iconPicture);
    }

    private static Image getIconByPicture(IconPicture iconPicture) {
        return getImageByAbsolutePath(ICONS_LOCATION + iconPicture.fileName);
    }

    public enum IconPicture {
        GREY_PLUS("grey_plus.png"), OK("ok.png"), GREEN_TICK("green_tick.png"),
        RED_CROSS("red_cross.png"), KIKI_ORG_LOGO("kiki_org_logo.png"), DELTE("delete.png"),
        GREY_CROSS("gray_cross.png"), GREEN_PLUS("green_plus.png"), VOC_LOGO("vocabulary_logo.png"),
        SUMMER("summer.png"), WINTER("winter.png"), AUTUMN("autumn.png"),
        HISTORY_COVER("history_cover.png");

        private final String fileName;

        IconPicture(String fileName) {
            this.fileName = fileName;
        }
    }

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
