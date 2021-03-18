package com.katyshevtseva.fx;

public class Styler {

    private Styler() {

    }

    public enum StandardColor {
        GRAY("#808080"), BLACK("#000000"), GREEN("#008000"), PURPLE("#800080"),
        BLUE("#4C9FFF"), ORANGE("#FFA24C"), SCREAMING_GREEN("#4FFF4C");

        private String code;

        StandardColor(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

    public enum ThingToColor {
        BACKGROUND(" -fx-background-color: "), TEXT(" -fx-text-fill: ");

        private String text;

        ThingToColor(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }

    public static String getColorfullStyle(ThingToColor thingToColor, StandardColor standardColor) {
        return getColorfullStyle(thingToColor, standardColor.getCode());
    }

    public static String getColorfullStyle(ThingToColor thingToColor, String color) {
        return thingToColor.getText() + color + ";";
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

    public static String getNotItalicTextStyle() {
        return " -fx-font-style: normal; ";
    }

    public static String getTextSizeStyle(int size) {
        return " -fx-font-size: " + size + "; ";
    }
}
