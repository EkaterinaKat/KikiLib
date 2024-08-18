package com.katyshevtseva.fx.dialogconstructor;

import com.katyshevtseva.fx.Size;
import com.katyshevtseva.time.TimeNode;
import javafx.scene.Node;
import javafx.scene.control.Control;

import java.util.Arrays;
import java.util.List;

public class DcTimeField implements DcElement {
    private final TimeNode timeNode;
    private final Long totalMinInitValue;
    private final boolean required;
    private boolean disabled = false;

    public DcTimeField(boolean required, Long totalMinInitValue) {
        this.totalMinInitValue = totalMinInitValue;
        timeNode = new TimeNode();
        this.required = required;
    }

    public int getValue() {
        return timeNode.getTotalMin();
    }

    @Override
    public boolean isRequired() {
        return required;
    }

    @Override
    public List<Control> getControls() {
        return Arrays.asList(timeNode.getHourTF(), timeNode.getMinTF());
    }

    @Override
    public Size getSize() {
        return new Size(100, 400);
    }

    @Override
    public void setInitValue() {
        timeNode.setTotalMin((int) (totalMinInitValue == null ? 0 : totalMinInitValue));
    }

    @Override
    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    @Override
    public Node getNode() {
        return timeNode.getNode();
    }
}
