package com.katyshevtseva.fx.dialog.controller;

import com.katyshevtseva.fx.Utils;
import com.katyshevtseva.fx.WindowBuilder;
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
    private final AnswerHandler answerHandler;

    public QuestionDialogController(String question, AnswerHandler answerHandler) {
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
        answerHandler.handle(b);
        Utils.closeWindowThatContains(questionLabel);
    }

    @FunctionalInterface
    public interface AnswerHandler {
        void handle(boolean b);
    }
}
