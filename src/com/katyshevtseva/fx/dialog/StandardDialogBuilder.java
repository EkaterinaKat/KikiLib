package com.katyshevtseva.fx.dialog;

import com.katyshevtseva.fx.WindowBuilder;
import com.katyshevtseva.fx.WindowBuilder.FxController;
import com.katyshevtseva.fx.dialog.controller.*;
import com.katyshevtseva.fx.dialog.controller.ImageSelectDialogController.ImageContainer;
import com.katyshevtseva.fx.dialog.controller.QuestionDialogController.AnswerHandler;
import com.katyshevtseva.general.OneArgKnob;

import java.util.List;

public class StandardDialogBuilder {
    private final String DIALOG_FXML_LOCATION = "/com/katyshevtseva/fx/dialog/fxml/";
    private int dialogWidth = 340;
    private int dialogHeight = 200;
    private String title = "";
    private boolean isModal = true;

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

    public StandardDialogBuilder setModality(boolean isModal) {
        this.isModal = isModal;
        return this;
    }

    public void openQuestionDialog(String question, AnswerHandler answerHandler) {
        QuestionDialogController controller = new QuestionDialogController(question, answerHandler);
        getWindowBuilder("question_dialog.fxml", controller).showWindow();
    }

    public void openInfoDialog(String info) {
        InfoDialogController controller = new InfoDialogController(info);
        getWindowBuilder("info_dialog.fxml", controller).showWindow();
    }

    public void openTextFieldAndTextAreaDialog(String initFirstText, String initSecondText,
                                               TextFieldAndTextAreaDialogController.OkButtonHandler okButtonHandler) {
        TextFieldAndTextAreaDialogController controller =
                new TextFieldAndTextAreaDialogController(initFirstText, initSecondText, okButtonHandler);
        getWindowBuilder("text_field_and_text_area_dialog.fxml", controller).showWindow();
    }

    //340x200 хорошо подходит для этого окна
    public <T> void openTextFieldAndComboBoxDialog(String initText, List<T> comboBoxItems, T initComboBoxItem,
                                                   TextFieldAndComboBoxDialogController.OkButtonHandler<T> okButtonHandler) {
        TextFieldAndComboBoxDialogController<T> controller = new TextFieldAndComboBoxDialogController<T>(
                initText, comboBoxItems, initComboBoxItem, okButtonHandler);
        getWindowBuilder("text_field_and_combobox_dialog.fxml", controller).showWindow();
    }

    public <T> void openComboBoxDialog(List<T> comboBoxItems, T initComboBoxItem,
                                       ComboBoxDialogController.OkButtonHandler<T> okButtonHandler) {
        ComboBoxDialogController<T> controller = new ComboBoxDialogController<T>(comboBoxItems, initComboBoxItem, okButtonHandler);
        getWindowBuilder("combobox_dialog.fxml", controller).showWindow();
    }

    public void openTextFieldDialog(String initText, TextFieldDialogController.OkButtonHandler okButtonHandler) {
        TextFieldDialogController controller = new TextFieldDialogController(initText, okButtonHandler);
        getWindowBuilder("text_field_dialog.fxml", controller).showWindow();
    }

    public TwoTextFieldsDialogController openTwoTextFieldsDialog(String initText1, String initText2, boolean closeAfterOk,
                                                                 TwoTextFieldsDialogController.OkButtonHandler okButtonHandler) {
        TwoTextFieldsDialogController controller = new TwoTextFieldsDialogController(initText1, initText2, closeAfterOk, okButtonHandler);
        getWindowBuilder("two_text_fields_dialog.fxml", controller).showWindow();
        return controller;
    }

    public ImageSelectDialogController openImageSelectionDialog(
            List<ImageContainer> imageContainers, OneArgKnob<ImageContainer> selectionListener) {

        ImageSelectDialogController controller = new ImageSelectDialogController(
                imageContainers,
                selectionListener);

        getWindowBuilder("image_select_dialog.fxml", controller)
                .setWidth(controller.getWindowWidth())
                .setHeight(controller.getWindowHeight())
                .showWindow();

        return controller;
    }

    public ImageSelectDialogController openEditableImageSelectionDialog(
            List<ImageContainer> imageContainers,
            List<ImageContainer> addableImageContainers,
            OneArgKnob<ImageContainer> selectionListener,
            OneArgKnob<List<ImageContainer>> windowCloseListener) {

        EditableImageSelectDialogController controller = new EditableImageSelectDialogController(
                imageContainers,
                addableImageContainers,
                selectionListener,
                windowCloseListener);

        getWindowBuilder("image_select_dialog.fxml", controller)
                .setWidth(controller.getWindowWidth())
                .setHeight(controller.getWindowHeight())
                .setOnWindowCloseEventHandler(event -> windowCloseListener.execute(controller.getImageContainers()))
                .showWindow();

        return controller;
    }

    private WindowBuilder getWindowBuilder(String fxmlName, FxController controller) {
        return new WindowBuilder(DIALOG_FXML_LOCATION + fxmlName)
                .setHeight(dialogHeight).setWidth(dialogWidth).setTitle(title)
                .setController(controller).setModal(isModal);
    }
}
