package com.katyshevtseva.fx;

import com.katyshevtseva.color.ColorUtils;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Label;

public class Styler {

    private Styler() {

    }

    public enum StandardColor {
        GRAY("#808080"), BLACK("#000000"), GREEN("#008000"), PURPLE("#800080"),
        BLUE("#4C9FFF"), ORANGE("#FFA24C"), SCREAMING_GREEN("#4FFF4C"), RED("#cf2121"),
        WHITE("#FFFFFF"), PASTEL_PINK("#FFD1DC"), PEACH("#FFE5B4"), BROWN("#4F250C"),
        GOLD("#FFC30A"), DARK_BLUE("#002137"), LIGHT_GREY("#C7C7C7");

        private String code;

        StandardColor(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

    public enum ThingToColor {
        BACKGROUND(" -fx-background-color: "),
        TEXT(" -fx-text-fill: "),
        TABLE_TEXT(" -fx-text-background-color: "),
        BORDER(" -fx-border-color: ");

        private String text;

        ThingToColor(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }

    public static void setBackgroundColorAndCorrectTextColor(Node node, Label label, String color) {
        node.setStyle(getColorfullStyle(Styler.ThingToColor.BACKGROUND, color));

        if (ColorUtils.isDark(color))
            label.setStyle(Styler.getColorfullStyle(Styler.ThingToColor.TEXT, Styler.StandardColor.WHITE));
    }

    public static String getColorfullStyle(ThingToColor thingToColor, StandardColor standardColor) {
        return getColorfullStyle(thingToColor, standardColor.getCode());
    }

    public static String getColorfullStyle(ThingToColor thingToColor, String color) {
        return thingToColor.getText() + color + "; ";
    }

    public static String getBoldTextStyle() {
        return " -fx-font-weight: bold; ";
    }

    public static String getNotBoldTextStyle() {
        return " -fx-font-weight: normal; ";
    }

    public static String getItalicTextStyle() {
        return " -fx-font-style: italic; ";
    }

    public static String getUnderlineTextStyle() {
        return " -fx-underline: true; ";
    }

    public static String getNotItalicTextStyle() {
        return " -fx-font-style: normal; ";
    }

    public static String getTextSizeStyle(int size) {
        return " -fx-font-size: " + size + "; ";
    }

    public static String getBlackBorderStyle() {
        return " -fx-border-color: #000000; ";
    }

    public static String getBorderWidth(int width) {
        return String.format(" -fx-border-width: %d; ", width);
    }

    public static String getBorderRadius(int radius) {
        return String.format(" -fx-border-radius: %d; ", radius);
    }

    public static void setHoverStyle(Node node, String hoverStyle) {
        String initStyle = node.getStyle();

        node.hoverProperty().addListener(
                (ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean show) -> {
                    if (show) {
                        node.setStyle(hoverStyle);
                    } else {
                        node.setStyle(initStyle);
                    }
                });
    }
}
