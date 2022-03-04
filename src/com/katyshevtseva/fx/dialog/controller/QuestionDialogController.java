package com.katyshevtseva.fx.dialog.controller;

import com.katyshevtseva.fx.FxUtils;
import com.katyshevtseva.fx.WindowBuilder;
import com.katyshevtseva.general.OneArgKnob;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class QuestionDialogController implements WindowBuilder.FxController {
    @FXML
    private Button yesButton;
    @FXML
    private Button noButton;
    @FXML
    private Label questionLabel;
    private final String question;
    private final OneArgKnob<Boolean> answerHandler;

    public QuestionDialogController(String question, OneArgKnob<Boolean> answerHandler) {
        this.question = question;
        this.answerHandler = answerHandler;
    }

    @FXML
    private void initialize() {
        questionLabel.setText(question);
        yesButton.setOnAction(event -> handleAnswer(true));
        noButton.setOnAction(event -> handleAnswer(false));
    }

    private void handleAnswer(boolean b) {
        answerHandler.execute(b);
        FxUtils.closeWindowThatContains(questionLabel);
    }
}
