package com.katyshevtseva.fx.dialogconstructor;

import com.katyshevtseva.fx.Size;
import com.katyshevtseva.general.GeneralUtils;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import java.util.Collections;
import java.util.List;

public class DcFloatNumField implements DcElement {
    private final boolean required;
    private final TextField textField;
    private final Float initValue;

    public DcFloatNumField(boolean required, Float initValue) {
        this(required, initValue, null);
    }

    public DcFloatNumField(boolean required, Float initValue, String hint) {
        this.required = required;
        textField = new TextField(initValue != null ? initValue + "" : "");
        if (hint != null)
            textField.setPromptText(hint);
        this.initValue = initValue;


        textField.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*[.]?\\d*")) {
                return change;
            }
            return null;
        }));
    }

    public Float getValue() {
        if (GeneralUtils.isEmpty(textField.getText()))
            return null;
        return Float.parseFloat(textField.getText());
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
