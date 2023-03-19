package com.katyshevtseva.fx.dialogconstructor;

import com.katyshevtseva.fx.FxUtils;
import com.katyshevtseva.fx.Size;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public class DcDatePicker implements DcElement {
    private final boolean required;
    private final DatePicker datePicker;
    private final Date initValue;
    private boolean disabled = false;

    public DcDatePicker(boolean required, Date initValue) {
        this.required = required;
        this.initValue = initValue;
        datePicker = new DatePicker();
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public Date getValue() {
        return FxUtils.getDate(datePicker);
    }

    @Override
    public boolean isRequired() {
        return required;
    }

    @Override
    public List<Control> getControls() {
        return Collections.singletonList(datePicker);
    }

    @Override
    public Size getSize() {
        return new Size(100, 400);
    }

    @Override
    public void setInitValue() {
        if (initValue != null)
            FxUtils.setDate(datePicker, initValue);
    }

    @Override
    public boolean isDisabled() {
        return disabled;
    }
}