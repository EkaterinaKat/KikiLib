package com.katyshevtseva.fx.dialogconstructor;

import com.katyshevtseva.fx.Size;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;

import java.util.Collections;
import java.util.List;

public class DcTextField implements DcElement {
    private final boolean required;
    private final TextField textField;
    private final String initValue;

    public DcTextField(boolean required, String initValue) {
        this.required = required;
        textField = new TextField(initValue);
        this.initValue = initValue;
    }

    public String getValue() {
        return textField.getText();
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
        textField.setText(initValue);
    }

    @Override
    public boolean isDisabled() {
        return false;
    }
}
