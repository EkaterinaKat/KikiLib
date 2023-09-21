package com.katyshevtseva.fx.dialogconstructor;

import com.katyshevtseva.fx.FxUtils;
import com.katyshevtseva.fx.Size;
import com.katyshevtseva.general.GeneralUtils;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;

import java.util.Collections;
import java.util.List;

public class DcNumField implements DcElement {
    private final boolean required;
    private final TextField textField;
    private final Long initValue;

    public DcNumField(boolean required, Long initValue) {
        this.required = required;
        textField = new TextField(initValue != null ? initValue + "" : "");
        FxUtils.disableNonNumericChars(textField);
        this.initValue = initValue;
    }

    public Long getValue() {
        if (GeneralUtils.isEmpty(textField.getText()))
            return null;
        return Long.parseLong(textField.getText());
    }

    @Override
    public boolean isRequired() {
        return required;
    }

    @Override
    public List<Control> getControls() {
        return Collections.singletonList(textField);
    }

    @Override
    public Size getSize() {
        return new Size(100, 400);
    }

    @Override
    public void setInitValue() {
        textField.setText(initValue != null ? initValue + "" : "");
    }

    @Override
    public boolean isDisabled() {
        return false;
    }
}
