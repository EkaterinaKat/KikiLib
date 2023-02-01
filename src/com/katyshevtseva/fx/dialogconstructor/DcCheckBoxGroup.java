package com.katyshevtseva.fx.dialogconstructor;

import com.katyshevtseva.fx.Size;
import com.katyshevtseva.general.HasShortInfo;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DcCheckBoxGroup<E extends HasShortInfo> implements DcElement {
    private final List<E> allValues;
    private final List<E> initValues;
    private Map<CheckBox, E> checkBoxValueMap;
    private boolean disabled = false;

    public DcCheckBoxGroup(List<E> allValues) {
        this.allValues = allValues;
        initValues = new ArrayList<>();
        initMap();
    }

    public DcCheckBoxGroup(List<E> allValues, List<E> initValues) {
        this.allValues = allValues;
        this.initValues = initValues;
        initMap();
    }

    private void initMap() {
        checkBoxValueMap = new HashMap<>();
        for (E e : allValues) {
            CheckBox checkBox = new CheckBox(e.getShortInfo());
            checkBox.setSelected(initValues.contains(e));
            checkBoxValueMap.put(checkBox, e);
        }
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public List<E> getSelectedElements() {
        return checkBoxValueMap.entrySet().stream()
                .filter(entry -> entry.getKey().isSelected())
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isRequired() {
        return false;
    }

    @Override
    public List<Control> getControls() {
        return new ArrayList<>(checkBoxValueMap.keySet());
    }

    @Override
    public Size getSize() {
        return new Size(40 * allValues.size(), 400);
    }

    @Override
    public void setInitValue() {
        checkBoxValueMap.forEach((key, value) -> key.setSelected(initValues.contains(value)));
    }

    @Override
    public boolean isDisabled() {
        return disabled;
    }
}
