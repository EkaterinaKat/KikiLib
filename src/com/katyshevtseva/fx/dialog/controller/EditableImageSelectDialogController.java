package com.katyshevtseva.fx.dialog.controller;

import com.katyshevtseva.fx.ImageContainer;
import com.katyshevtseva.fx.dialog.StandardDialogBuilder;
import com.katyshevtseva.general.OneArgKnob;
import javafx.geometry.HPos;
import javafx.geometry.Rectangle2D;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;

import static com.katyshevtseva.fx.FxImageCreationUtil.IconPicture.GREY_PLUS;
import static com.katyshevtseva.fx.FxImageCreationUtil.getIcon;
import static com.katyshevtseva.general.GeneralUtils.getColumnByIndexAndColumnNum;
import static com.katyshevtseva.general.GeneralUtils.getRowByIndexAndColumnNum;

public class EditableImageSelectDialogController extends ImageSelectDialogController {
    private final List<ImageContainer> addableImageContainers;
    private final OneArgKnob<List<ImageContainer>> windowCloseListener;

    public EditableImageSelectDialogController(List<ImageContainer> imageContainers,
                                               List<ImageContainer> addableImageContainers,
                                               OneArgKnob<ImageContainer> selectionListener,
                                               OneArgKnob<List<ImageContainer>> windowCloseListener) {
        super(imageContainers, selectionListener);
        this.addableImageContainers = addableImageContainers;
        this.windowCloseListener = windowCloseListener;
    }

    private List<ImageContainer> getFreeImages() {
        return addableImageContainers.stream()
                .filter(imageContainer -> !imageContainers.contains(imageContainer)).collect(Collectors.toList());
    }

    @Override
    int getNumOfCells() {
        return imageContainers.size() + 1;
    }

    @Override
    void setContent() {
        super.setContent();

        gridPane.add(getAddImageButton(), getColumnByIndexAndColumnNum(imageContainers.size(), MAX_NUM_OF_COLUMNS),
                getRowByIndexAndColumnNum(imageContainers.size(), MAX_NUM_OF_COLUMNS));

    }

    @Override
    void handleImageViewClick(ImageContainer imageContainer) {
        super.handleImageViewClick(imageContainer);
        windowCloseListener.execute(imageContainers);
    }

    @Override
    void addImageToGridPane(int index) {
        super.addImageToGridPane(index);

        if (imageContainers.size() > 1) {
            Button button = new Button("[x]");
            gridPane.add(button, getColumnByIndexAndColumnNum(index, MAX_NUM_OF_COLUMNS),
                    getRowByIndexAndColumnNum(index, MAX_NUM_OF_COLUMNS));
            GridPane.setHalignment(button, HPos.RIGHT);
            GridPane.setValignment(button, VPos.TOP);
            button.setOnAction(event -> {
                imageContainers.remove(imageContainers.get(index));
                setContent();
                setWindowAndScrollPaneSize();
            });
        }
    }

    private ImageView getAddImageButton() {
        ImageView imageView = new ImageView(getIcon(GREY_PLUS));
        imageView.setFitHeight(IMAGE_SIZE);
        imageView.setFitWidth(IMAGE_SIZE);
        imageView.setOnMouseClicked(event -> new StandardDialogBuilder().openImageSelectionDialog(getFreeImages(), imageContainer -> {
            imageContainers.add(imageContainer);
            setContent();
            setWindowAndScrollPaneSize();
        }));
        return imageView;
    }

    private void setWindowAndScrollPaneSize() {
        scrollPane.setPrefWidth(getScrollPaneWidth());
        scrollPane.setPrefHeight(getScrollPaneHeight());

        Stage stage = (Stage) scrollPane.getScene().getWindow();
        stage.setResizable(true);

        stage.setMaxHeight(getWindowHeight());
        stage.setMinHeight(getWindowHeight());
        stage.setMaxWidth(getWindowWidth());
        stage.setMinWidth(getWindowWidth());

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - stage.getMaxWidth()) / 2);
        stage.setY((screenBounds.getHeight() - stage.getMaxHeight()) / 3);
    }
}
