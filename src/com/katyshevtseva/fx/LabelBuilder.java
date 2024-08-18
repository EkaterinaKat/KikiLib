package com.katyshevtseva.fx;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;

public class LabelBuilder {
    private Integer width;
    private Integer minWidth;
    private Integer textSize;
    private String text;
    private String color;
    private boolean centerAligment = false;

    public LabelBuilder width(int width) {
        this.width = width;
        return this;
    }

    public LabelBuilder minWidth(int width) {
        this.minWidth = width;
        return this;
    }

    public LabelBuilder setCenterAligment() {
        centerAligment = true;
        return this;
    }

    public LabelBuilder textSize(int textSize) {
        this.textSize = textSize;
        return this;
    }

    public LabelBuilder text(String text) {
        this.text = text;
        return this;
    }

    public LabelBuilder color(String color) {
        this.color = color;
        return this;
    }

    public LabelBuilder color(Styler.StandardColor color) {
        this.color = color.getCode();
        return this;
    }

    public Label build() {
        Label label = new Label();

        if (width != null) {
            label.setMaxWidth(width);
            label.setWrapText(true);
        }
        if (minWidth != null) {
            label.setMinWidth(minWidth);
        }
        if (textSize != null) {
            label.setStyle(Styler.getTextSizeStyle(textSize));
        }
        if (text != null) {
            label.setText(text);
        }
        if (color != null) {
            label.setStyle(label.getStyle() + Styler.getColorfullStyle(Styler.ThingToColor.TEXT, color));
        }
        if (centerAligment) {
            label.setAlignment(Pos.CENTER);
            label.setTextAlignment(TextAlignment.CENTER);
        }
        return label;
    }
}
