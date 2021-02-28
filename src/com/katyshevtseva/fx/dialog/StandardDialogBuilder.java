package com.katyshevtseva.fx.dialog;

import com.katyshevtseva.fx.WindowBuilder;
import com.katyshevtseva.fx.dialog.controller.InfoDialogController;
import com.katyshevtseva.fx.dialog.controller.QuestionDialogController;
import com.katyshevtseva.fx.dialog.controller.QuestionDialogController.AnswerHandler;
import com.katyshevtseva.fx.dialog.controller.TextFieldAndTextAreaDialogController;
import com.katyshevtseva.fx.dialog.controller.TextFieldAndTextAreaDialogController.OkButtonHandler;

public class StandardDialogBuilder {
    private final String DIALOG_FXML_LOCATION = "/com/katyshevtseva/fx/dialog/fxml/";
    private int dialogWidth = 340;
    private int dialogHeight = 200;
    private String title = "";
    private String cssPath;
    private String iconPath;

    public StandardDialogBuilder setDialogWidth(int width) {
        dialogWidth = width;
        return this;
    }

    public StandardDialogBuilder setDialogHeight(int height) {
        dialogHeight = height;
        return this;
    }

    public StandardDialogBuilder setTitle(String width) {
        this.title = title;
        return this;
    }

    public StandardDialogBuilder setCssPath(String cssPath) {
        this.cssPath = cssPath;
        return this;
    }

    public StandardDialogBuilder setIconPath(String iconPath) {
        this.iconPath = iconPath;
        return this;
    }

    public void openQuestionDialog(String question, AnswerHandler answerHandler) {
        WindowBuilder windowBuilder = new WindowBuilder(DIALOG_FXML_LOCATION + "question_dialog.fxml")
                .setHeight(dialogHeight).setWidth(dialogWidth).setTitle(title);
        if (iconPath != null)
            windowBuilder.setIconImagePath(iconPath);
        if (cssPath != null)
            windowBuilder.setCssPath(cssPath);
        windowBuilder.setController(new QuestionDialogController(question, answerHandler)).showWindow();
    }

    public void openInfoDialog(String info) {
        WindowBuilder windowBuilder = new WindowBuilder(DIALOG_FXML_LOCATION + "info_dialog.fxml")
                .setHeight(dialogHeight).setWidth(dialogWidth).setTitle(title);
        if (iconPath != null)
            windowBuilder.setIconImagePath(iconPath);
        if (cssPath != null)
            windowBuilder.setCssPath(cssPath);
        windowBuilder.setController(new InfoDialogController(info)).showWindow();
    }

    public void openTextFieldAndTextAreaDialog(String initFirstText, String initSecondText, OkButtonHandler okButtonHandler) {
        WindowBuilder windowBuilder = new WindowBuilder(DIALOG_FXML_LOCATION + "text_field_and_text_area_dialog.fxml")
                .setHeight(dialogHeight).setWidth(dialogWidth).setTitle(title);
        if (iconPath != null)
            windowBuilder.setIconImagePath(iconPath);
        if (cssPath != null)
            windowBuilder.setCssPath(cssPath);
        windowBuilder.setController(new TextFieldAndTextAreaDialogController(initFirstText, initSecondText, okButtonHandler)).showWindow();
    }


}
