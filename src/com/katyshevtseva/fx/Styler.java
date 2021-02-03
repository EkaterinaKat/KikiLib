package com.katyshevtseva.fx;

public class Styler {

    private Styler() {

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
}
