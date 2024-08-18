package com.katyshevtseva.fx.dialogconstructor;

import com.katyshevtseva.fx.Size;
import javafx.scene.control.Control;
import javafx.scene.control.TextArea;

import java.util.Collections;
import java.util.List;

public class DcTextArea implements DcElement {
    private final boolean required;
    private final TextArea textArea;
    private final String initValue;

    public DcTextArea(boolean required, String initValue) {
        this(required, initValue, null);
    }

    public DcTextArea(boolean required, String initValue, String hint) {
        this.required = required;
        textArea = new TextArea(initValue);
        textArea.setWrapText(true);
        this.initValue = initValue;
        if (hint != null)
            textArea.setPromptText(hint);
    }

    public String getValue() {
        return textArea.getText();
    }

    @Override
    public boolean isRequired() {
        return required;
    }

    @Override
    public List<Control> getControls() {
        return Collections.singletonList(textArea);
    }

    @Override
    public Size getSize() {
        return new Size(250, 400);
    }

    @Override
    public void setInitValue() {
        textArea.setText(initValue);
    }

    @Override
    public boolean isDisabled() {
        return false;
    }
}
