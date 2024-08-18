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
    private Button okButton;

    public DialogController(List<DcElement> elements, NoArgsKnob onConfirmHandler, Integer height) {
        this.elements = elements;
        this.onConfirmHandler = onConfirmHandler;

        int windowHeight = height != null ? height :
                elements.stream().mapToInt(dcElement -> dcElement.getSize().getHeight()).sum()
                        + 70 - (15 * (elements.size() - 1));

        int windowWidth = elements.stream().mapToInt(dcElement -> dcElement.getSize().getWidth())
                .max().orElseThrow(RuntimeException::new);

        windowSize = new Size(windowHeight, windowWidth);
        controlsWidth = windowSize.getWidth() - 60;
    }

    @FXML
    private void initialize() {
        container.getChildren().add(getPaneWithHeight(20));
        for (DcElement element : elements) {
            if (element.getNode() == null) {
                element.getControls().forEach(control -> setWidth(control, controlsWidth));
                element.getControls().forEach(control -> container.getChildren().addAll(control, getPaneWithHeight(10)));
            } else {
                container.getChildren().addAll(element.getNode(), getPaneWithHeight(10));
            }
            element.setInitValue();
            element.getControls().forEach(control -> control.setDisable(element.isDisabled()));
        }

        initOkButton();
        container.getChildren().add(okButton);
        handleRequiredControls();
    }

    private void initOkButton() {
        okButton = new Button("Ok");
        setWidth(okButton, controlsWidth);
        okButton.setOnAction(event -> {
            onConfirmHandler.execute();
            closeWindowThatContains(container);
        });
    }

    private void handleRequiredControls() {
        List<Control> requiredControls = elements.stream().filter(DcElement::isRequired)
                .flatMap(dcElement -> dcElement.getControls().stream()).collect(Collectors.toList());
        if (!requiredControls.isEmpty()) {
            associateButtonWithControls(okButton, requiredControls);
        }
    }
}
