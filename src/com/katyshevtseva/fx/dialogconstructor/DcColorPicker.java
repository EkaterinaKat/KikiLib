package com.katyshevtseva.fx.dialogconstructor;

import com.katyshevtseva.color.ColorUtils;
import com.katyshevtseva.fx.Size;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Control;
import javafx.scene.paint.Color;

import java.util.Collections;
import java.util.List;

public class DcColorPicker implements DcElement {
    private final boolean required;
    private final ColorPicker colorPicker;
    private final String initValue;
    private boolean disabled = false;

    public DcColorPicker(boolean required, String initValue) {
        this.required = required;
        this.initValue = initValue;
        colorPicker = new ColorPicker();
        if (initValue != null)
            colorPicker.setValue(Color.web(initValue));
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public String getValue() {
        return ColorUtils.toHexString(colorPicker.getValue());
    }

    @Override
    public boolean isRequired() {
        return required;
    }

    @Override
    public List<Control> getControls() {
        return Collections.singletonList(colorPicker);
    }

    @Override
    public Size getSize() {
        return new Size(100, 400);
    }

    @Override
    public void setInitValue() {
        if (initValue != null)
            colorPicker.setValue(Color.web(initValue));
    }

    @Override
    public boolean isDisabled() {
        return disabled;
    }
}