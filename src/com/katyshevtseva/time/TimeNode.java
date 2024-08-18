package com.katyshevtseva.time;

import com.katyshevtseva.fx.FxUtils;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import lombok.Getter;

@Getter
public class TimeNode {
    private final TextField minTF;
    private final TextField hourTF;
    private final HBox node;

    public TimeNode(int totalMin) {
        this();
        setTotalMin(totalMin);
    }

    public TimeNode() {
        minTF = getTimeTF();
        hourTF = getTimeTF();

        HBox hBox = new HBox();
        hBox.getChildren().addAll(
                new Label("hour:"),
                hourTF,
                FxUtils.getPaneWithWidth(10),
                new Label("min:"),
                minTF);
        node = hBox;
    }

    private TextField getTimeTF() {
        TextField tf = new TextField();
        FxUtils.disableNonNumericChars(tf);
        FxUtils.setWidth(tf, 70);
        tf.setText("0");
        return tf;
    }

    public int getTotalMin() {
        System.out.println(TimeUtil.getTotalMin(hourTF, minTF));
        return TimeUtil.getTotalMin(hourTF, minTF);
    }

    public void setTotalMin(int minutes) {
        System.out.println(minutes);
        TimeUtil.setTime(minutes, hourTF, minTF);
    }
}
