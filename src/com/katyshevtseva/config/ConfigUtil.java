package com.katyshevtseva.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import static com.katyshevtseva.config.ConfigConstants.CONFIG_FILE_NAME;

public class ConfigUtil {
    private final Properties configs;

    public ConfigUtil() {
        File configFile = getConfigFileOrNull();

        if (configFile == null)
            configFile = getConfigFileFromBuiltProject();

        configs = new Properties();
        try {
            configs.load(new FileInputStream(Objects.requireNonNull(configFile)));
        } catch (IOException | NullPointerException e) {
            throw new RuntimeException("Файл конфигурации не найден или произошла ошибка при его чтении");
        }
    }

    public String getConfigOrNull(String name) {
        return configs.getProperty(name);
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
        try {
            for (File file : necessaryDir.listFiles()) {
                if (file.getName().equals(CONFIG_FILE_NAME))
                    return file;
            }
        } catch (Exception e) {

        }
        return null;
    }
}
