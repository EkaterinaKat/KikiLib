package com.katyshevtseva.fx.switchcontroller;

import com.katyshevtseva.fx.WindowBuilder.FxController;
import com.katyshevtseva.general.OneArgKnob;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.util.List;

public abstract class AbstractSwitchController implements FxController {
    private List<Section> sections;
    private Pane pane;

    public void init(List<Section> sections, Pane pane, OneArgKnob<Button> buttonArranger) {

        if (pane == null || sections == null || sections.isEmpty()) {
            throw new RuntimeException("***** pane==null ||sections==null||sections.isEmpty() *****");
        }

        this.sections = sections;
        this.pane = pane;

        sections.forEach(section -> {
            section.getButton().setOnAction(event -> openSection(section));
            buttonArranger.execute(section.getButton());
        });
        openSection(sections.get(0));
    }

    private void openSection(Section section) {
        for (Section section1 : sections) {
            section1.getButton().setDisable(false);
        }
        section.getButton().setDisable(true);

        pane.getChildren().clear();
        pane.getChildren().add(section.getNode());
    }
}