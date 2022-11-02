package com.katyshevtseva.fx.dialog;

import com.katyshevtseva.fx.ImageContainer;
import com.katyshevtseva.fx.Size;
import com.katyshevtseva.fx.WindowBuilder;
import com.katyshevtseva.fx.WindowBuilder.FxController;
import com.katyshevtseva.fx.dialog.controller.*;
import com.katyshevtseva.general.OneArgKnob;
import com.katyshevtseva.history.Action;
import com.katyshevtseva.history.HasHistory;
import javafx.scene.Node;

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
