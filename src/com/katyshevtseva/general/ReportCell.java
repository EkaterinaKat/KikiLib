package com.katyshevtseva.general;

import javafx.scene.control.ContextMenu;
import lombok.Builder;
import lombok.Getter;

import static com.katyshevtseva.fx.Styler.StandardColor.WHITE;

@Builder
@Getter
public class ReportCell {
    @Builder.Default
    private String text = "";
    @Builder.Default
    private String color = WHITE.getCode();
    @Builder.Default
    private Type type = Type.REGULAR;
    private Integer width;
    private ContextMenu contextMenu;

    public boolean isColumnHead() {
        return type == Type.HEAD_COLUMN;
    }

    public enum Type {
        HEAD_COLUMN, REGULAR
    }
}
