package com.katyshevtseva.fx.dialog;

import com.katyshevtseva.fx.ImageContainer;
import com.katyshevtseva.fx.Size;
import com.katyshevtseva.fx.WindowBuilder;
import com.katyshevtseva.fx.WindowBuilder.FxController;
import com.katyshevtseva.fx.dialog.controller.*;
import com.katyshevtseva.general.OneArgKnob;
import com.katyshevtseva.general.TwoArgKnob;
import com.katyshevtseva.history.Action;
import com.katyshevtseva.history.HasHistory;
import javafx.scene.Node;

import java.util.Arrays;
import java.util.List;

public class StandardDialogBuilder {
    private static final String DIALOG_FXML_LOCATION = "/com/katyshevtseva/fx/dialog/fxml/";
    private Size size;
    private String title = "";
    private boolean isModal = true;

    public StandardDialogBuilder setSize(Size size) {
        this.size = size;
        return this;
    }

    public StandardDialogBuilder setSize(int height, int width) {
        this.size = new Size(height, width);
        return this;
    }

    public StandardDialogBuilder setSize(int size) {
        this.size = new Size(size, size);
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

    public void openNoFxmlContainerDialog(FxController controller) {
        getWindowBuilder("container.fxml", controller).showWindow();
    }

    public void openNodeContainerDialog(Node node) {
        getWindowBuilder("container.fxml", new NodeContainerController(node)).showWindow();
    }

    public void openQuestionDialog(String question, OneArgKnob<Boolean> answerHandler) {
        QuestionDialogController controller = new QuestionDialogController(question, answerHandler);
        getWindowBuilder("question_dialog.fxml", controller).showWindow();
    }

    public void openInfoDialog(String info) {
        InfoDialogController controller = new InfoDialogController(info);
        getWindowBuilder("info_dialog.fxml", controller).showWindow();
    }

    public void openTextFieldAndTextAreaDialog(TwoArgKnob<String, String> okButtonHandler) {
        openTextFieldAndTextAreaDialog("", "", okButtonHandler);
    }

    public void openTextFieldAndTextAreaDialog(String initFirstText, String initSecondText, TwoArgKnob<String, String> okButtonHandler) {
        if (size == null) {
            size = new Size(340, 450);
        }

        TextFieldAndTextAreaDialogController controller =
                new TextFieldAndTextAreaDialogController(initFirstText, initSecondText, okButtonHandler, size);
        getWindowBuilder("text_field_and_text_area_dialog.fxml", controller).showWindow();
    }

    //340x200 хорошо подходит для этого окна
    public <T> TextFieldAndComboBoxDialogController<T> openTextFieldAndComboBoxDialog(String initText, List<T> comboBoxItems, T initComboBoxItem, TwoArgKnob<String, T> okButtonHandler) {
        TextFieldAndComboBoxDialogController<T> controller = new TextFieldAndComboBoxDialogController<>(
                initText, comboBoxItems, initComboBoxItem, okButtonHandler);
        getWindowBuilder("text_field_and_combobox_dialog.fxml", controller).showWindow();
        return controller;
    }

    public <T> TextFieldAndComboBoxDialogController<T> openTextFieldAndComboBoxDialog(T[] comboBoxItems, TwoArgKnob<String, T> okButtonHandler) {
        return openTextFieldAndComboBoxDialog(null, Arrays.asList(comboBoxItems), null, okButtonHandler);
    }

    public <T> void openComboBoxDialog(List<T> comboBoxItems, T initComboBoxItem,
                                       ComboBoxDialogController.OkButtonHandler<T> okButtonHandler) {
        ComboBoxDialogController<T> controller = new ComboBoxDialogController<>(comboBoxItems, initComboBoxItem, okButtonHandler);
        getWindowBuilder("combobox_dialog.fxml", controller).showWindow();
    }

    public void openTextFieldDialog(String initText, TextFieldDialogController.OkButtonHandler okButtonHandler) {
        TextFieldDialogController controller = new TextFieldDialogController(initText, okButtonHandler);
        getWindowBuilder("text_field_dialog.fxml", controller).showWindow();
    }

    public TwoTextFieldsDialogController openTwoTextFieldsDialog(String initText1, String initText2, boolean closeAfterOk,
                                                                 TwoArgKnob<String, String> okButtonHandler) {
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
                .setSize(controller.getWindowHeight(), controller.getWindowWidth())
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
                .setSize(controller.getWindowHeight(), controller.getWindowWidth())
                .setOnWindowCloseEventHandler(event -> windowCloseListener.execute(controller.getImageContainers()))
                .showWindow();

        return controller;
    }

    public <E extends HasHistory<A>, A extends Action<E>> HistoryDialogController<E, A> openHistoryDialog(E entity) {
        if (size == null) {
            size = new Size(600, 700);
        }

        HistoryDialogController<E, A> controller = new HistoryDialogController<>(entity, size);
        getWindowBuilder("history_dialog.fxml", controller).setSize(size).showWindow();
        return controller;
    }

    private WindowBuilder getWindowBuilder(String fxmlName, FxController controller) {
        if (size == null) {
            size = new Size(200, 340);
        }

        return new WindowBuilder(DIALOG_FXML_LOCATION + fxmlName)
                .setSize(size).setTitle(title).setController(controller).setModal(isModal);
    }
}
