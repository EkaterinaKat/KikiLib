package com.katyshevtseva.fx;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

class ConfigUtil {
    private final String CONFIG_FILE_NAME = "config.ini";
    private String iconImagePath;
    private String cssPath;

    ConfigUtil() {
        File configFile = getConfigFileOrNull();

        if (configFile == null)
            configFile = getConfigFileFromBuiltProject();

        if (configFile != null) {
            Properties properties = new Properties();
            try {
                properties.load(new FileInputStream(configFile));
            } catch (IOException e) {
                e.printStackTrace();
            }

            iconImagePath = properties.getProperty("ICON_IMAGE_PATH");
            cssPath = properties.getProperty("CSS_PATH");
        }
    }

    String getIconImagePath() {
        return iconImagePath;
    }

    String getCssPath() {
        return cssPath;
    }

    private File getConfigFileOrNull() {
        return getFiles(new File(".")).stream()
                .filter(file -> file.getName().equals(CONFIG_FILE_NAME)).findFirst().orElse(null);
    }

    private List<File> getFiles(File file) {
        if (file.isFile())
            return Collections.singletonList(file);

        List<File> result = new ArrayList<>();
        for (File file1 : file.listFiles()) {
            result.addAll(getFiles(file1));
        }

        return result;
    }

    // Костыль для того чтобы екзешник тоже мог найти config.ini
    private File getConfigFileFromBuiltProject() {
        File necessaryDir = new File(".").getAbsoluteFile()
                .getParentFile().getParentFile().getParentFile().getParentFile();
        for (File file : necessaryDir.listFiles()) {
            if (file.getName().equals(CONFIG_FILE_NAME))
                return file;
        }
        return null;
    }
}
