package com.katyshevtseva.fx.dialogconstructor;

import com.katyshevtseva.fx.Size;
import com.katyshevtseva.fx.WindowBuilder.FxController;
import com.katyshevtseva.general.NoArgsKnob;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.layout.VBox;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

import static com.katyshevtseva.fx.FxUtils.*;

public class DialogController implements FxController {
    private final List<DcElement> elements;
    private final NoArgsKnob onConfirmHandler;
    @Getter
    private final Size windowSize;
    private final int controlsWidth;
    @FXML
    private VBox container;

    public DialogController(List<DcElement> elements, NoArgsKnob onConfirmHandler) {
        this.elements = elements;
        this.onConfirmHandler = onConfirmHandler;

        windowSize = new Size(
                elements.stream().mapToInt(dcElement -> dcElement.getSize().getHeight()).sum()
                        + 70 - (15 * (elements.size() - 1)),
                elements.stream().mapToInt(dcElement -> dcElement.getSize().getWidth()).max().orElseThrow(RuntimeException::new)
        );
        controlsWidth = windowSize.getWidth() - 60;
    }

    @FXML
    private void initialize() {
        container.getChildren().add(getPaneWithHeight(20));
        for (DcElement element : elements) {
            setWidth(element.getControl(), controlsWidth);
            container.getChildren().addAll(element.getControl(), getPaneWithHeight(15));
            element.setInitValue();
            element.getControl().setDisable(element.isDisabled());
        }
        Button okButton = new Button("Ok");
        setWidth(okButton, controlsWidth);
        okButton.setOnAction(event -> {
            onConfirmHandler.execute();
            closeWindowThatContains(container);
        });
        container.getChildren().add(okButton);

        List<Control> requiredControls = elements.stream().filter(DcElement::isRequired)
                .map(DcElement::getControl).collect(Collectors.toList());
        if (!requiredControls.isEmpty()) {
            associateButtonWithControls(okButton, requiredControls);
        }
    }
}
