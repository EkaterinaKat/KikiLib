package com.katyshevtseva.fx.dialogconstructor;

import com.katyshevtseva.fx.Size;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;

public class DcCheckBox implements DcElement {
    private final CheckBox checkBox;
    private final boolean initValue;
    private boolean disabled = false;

    public DcCheckBox(boolean initValue, String text) {
        this.initValue = initValue;
        checkBox = new CheckBox(text);
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean getValue() {
        return checkBox.isSelected();
    }

    @Override
    public boolean isRequired() {
        return false;
    }

    @Override
    public Control getControl() {
        return checkBox;
    }

    @Override
    public Size getSize() {
        return new Size(100, 400);
    }

    @Override
    public void setInitValue() {
        checkBox.setSelected(initValue);
    }

    @Override
    public boolean isDisabled() {
        return disabled;
    }
}
