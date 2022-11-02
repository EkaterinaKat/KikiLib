package com.katyshevtseva.fx.dialogconstructor;

import com.katyshevtseva.fx.FxUtils;
import com.katyshevtseva.fx.Size;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;

import java.util.List;

public class DcComboBox<T> implements DcElement {
    private final boolean required;
    private final ComboBox<T> comboBox;
    private final T initValue;
    private boolean disabled = false;

    public DcComboBox(boolean required, T initValue, List<T> values) {
        this.required = required;
        this.initValue = initValue;
        comboBox = new ComboBox<>();
        FxUtils.setComboBoxItems(comboBox, values);
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public T getValue() {
        return comboBox.getValue();
    }

    @Override
    public boolean isRequired() {
        return required;
    }

    @Override
    public Control getControl() {
        return comboBox;
    }

    @Override
    public Size getSize() {
        return new Size(100, 400);
    }

    @Override
    public void setInitValue() {
        comboBox.setValue(initValue);
    }

    @Override
    public boolean isDisabled() {
        return disabled;
    }
}
