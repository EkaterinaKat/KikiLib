package com.katyshevtseva.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import static com.katyshevtseva.config.ConfigConstants.CONFIG_FILE_NAME;

public class ConfigUtil {
    private Properties configs;

    public ConfigUtil() {
        File configFile = getConfigFileOrNull();

        if (configFile == null)
            configFile = getConfigFileFromBuiltProject();

        if (configFile != null) {
            configs = new Properties();
            try {
                configs.load(new FileInputStream(configFile));
            } catch (IOException e) {
                throw new RuntimeException("Произошла ошибка при чтении файла конфигурации");
            }
        }
    }

    public String getConfigOrNull(String name) {
        if (configs == null) {
            return null;
        }
        return configs.getProperty(name);
    }

    public String getConfigOrThrowExeption(String name) {
        if (configs == null || configs.getProperty(name) == null) {
            throw new RuntimeException("Не удалось найти значение " + name);
        }
        return configs.getProperty(name);
    }

    private File getConfigFileOrNull() {
        return getFiles(new File("."), "out").stream()
                .filter(file -> file.getName().equals(CONFIG_FILE_NAME)).findFirst().orElse(null);
    }

    private List<File> getFiles(File file, String dirToIgnore) {
        if (file.isFile())
            return Collections.singletonList(file);

        if (file.getName().equals(dirToIgnore))
            return Collections.emptyList();

        List<File> result = new ArrayList<>();
        for (File file1 : file.listFiles()) {
            result.addAll(getFiles(file1, dirToIgnore));
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
