package com.katyshevtseva.color;

import javafx.scene.paint.Color;

public class ColorUtils {

    public static String toHexString(Color color) {
        int r = ((int) Math.round(color.getRed() * 255)) << 24;
        int g = ((int) Math.round(color.getGreen() * 255)) << 16;
        int b = ((int) Math.round(color.getBlue() * 255)) << 8;
        int a = ((int) Math.round(color.getOpacity() * 255));
        return String.format("#%08X", (r + g + b + a));
    }

    public static boolean isDark(String hexString) {
        java.awt.Color color = java.awt.Color.decode(hexString);
        return color.getBlue() + color.getGreen() + color.getRed() < 200;
    }
}
