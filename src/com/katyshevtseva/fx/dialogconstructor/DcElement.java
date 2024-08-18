package com.katyshevtseva.fx.dialogconstructor;

import com.katyshevtseva.fx.Size;
import javafx.scene.Node;
import javafx.scene.control.Control;

import java.util.List;

public interface DcElement {
    boolean isRequired();

    List<Control> getControls();

    Size getSize();

    void setInitValue();

    boolean isDisabled();

    default Node getNode() {
        return null;
    }
}
