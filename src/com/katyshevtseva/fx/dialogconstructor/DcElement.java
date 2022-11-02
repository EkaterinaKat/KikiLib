package com.katyshevtseva.fx.dialogconstructor;

import com.katyshevtseva.fx.Size;
import javafx.scene.control.Control;

public interface DcElement {
    boolean isRequired();

    Control getControl();

    Size getSize();

    void setInitValue();

    boolean isDisabled();
}
