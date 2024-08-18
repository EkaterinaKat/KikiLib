package com.katyshevtseva.general;

import javafx.scene.control.ContextMenu;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import static com.katyshevtseva.fx.Styler.StandardColor.WHITE;

@Builder
@Getter
public class ReportCell {
    @Builder.Default
    private String text = "";
    @Builder.Default
    private String color = WHITE.getCode();
    private String textColor;
    @Builder.Default
    private Type type = Type.REGULAR;
    private Integer width;
    @Setter
    private ContextMenu contextMenu;
    private Object item;

    public boolean isColumnHead() {
        return type == Type.HEAD_COLUMN;
    }

    public enum Type {
        HEAD_COLUMN, REGULAR
    }
}
