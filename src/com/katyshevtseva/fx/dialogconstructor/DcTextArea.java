package com.katyshevtseva.fx.dialogconstructor;

import com.katyshevtseva.fx.Size;
import javafx.scene.control.Control;
import javafx.scene.control.TextArea;

public class DcTextArea implements DcElement {
    private final boolean required;
    private final TextArea textArea;
    private final String initValue;

    public DcTextArea(boolean required, String initValue) {
        this.required = required;
        textArea = new TextArea(initValue);
        textArea.setWrapText(true);
        this.initValue = initValue;
    }

    public String getValue() {
        return textArea.getText();
    }

    @Override
    public boolean isRequired() {
        return required;
    }

    @Override
    public Control getControl() {
        return textArea;
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
