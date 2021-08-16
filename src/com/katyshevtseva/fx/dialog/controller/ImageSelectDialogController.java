package com.katyshevtseva.fx.dialog.controller;

import com.katyshevtseva.fx.FxUtils;
import com.katyshevtseva.fx.ImageContainer;
import com.katyshevtseva.fx.WindowBuilder.FxController;
import com.katyshevtseva.general.OneArgKnob;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Getter;

import java.util.List;

import static com.katyshevtseva.fx.FxUtils.getPaneWithHeight;
import static com.katyshevtseva.fx.FxUtils.getPaneWithWidth;
import static com.katyshevtseva.fx.ImageSizeUtil.getHeightByWidth;
import static com.katyshevtseva.fx.ImageSizeUtil.getWidthByHeight;
import static com.katyshevtseva.general.GeneralUtils.getColumnByIndexAndColumnNum;
import static com.katyshevtseva.general.GeneralUtils.getRowByIndexAndColumnNum;

public class ImageSelectDialogController implements FxController {
    static final int IMAGE_SIZE = 300;
    private static final int FRAME_SIZE = 30;
    private static final int GRID_GAP = 15;
    static final int MAX_NUM_OF_COLUMNS = 4;
    private static final int MAX_NUM_OF_VISIBLE_ROWS = 2;
    @Getter
    List<ImageContainer> imageContainers;
    private OneArgKnob<ImageContainer> selectionListener;
    @FXML
    ScrollPane scrollPane;
    GridPane gridPane;

    public ImageSelectDialogController(List<ImageContainer> imageContainers, OneArgKnob<ImageContainer> selectionListener) {
        this.imageContainers = imageContainers;
        this.selectionListener = selectionListener;
    }

    @FXML
    private void initialize() {
        initializeGridPane();
        scrollPane.setPrefWidth(getScrollPaneWidth());
        scrollPane.setPrefHeight(getScrollPaneHeight());
        setContent();
    }

    private void initializeGridPane() {
        gridPane = new GridPane();
        gridPane.setHgap(GRID_GAP);
        gridPane.setVgap(GRID_GAP);
        gridPane.setAlignment(Pos.CENTER);
        VBox vBox = new VBox();
        vBox.getChildren().addAll(getPaneWithHeight(FRAME_SIZE), gridPane, getPaneWithHeight(FRAME_SIZE));
        HBox hBox = new HBox();
        hBox.getChildren().addAll(getPaneWithWidth(FRAME_SIZE), vBox, getPaneWithWidth(FRAME_SIZE));
        scrollPane.setContent(hBox);
    }

    void setContent() {
        gridPane.getChildren().clear();

        for (int i = 0; i < imageContainers.size(); i++) {
            addImageToGridPane(i);
        }
    }

    void addImageToGridPane(int index) {
        ImageContainer imageContainer = imageContainers.get(index);
        ImageView imageView = new ImageView(imageContainer.getImage());

        if (getHeightByWidth(imageView, IMAGE_SIZE) <= IMAGE_SIZE) {
            imageView.setFitWidth(IMAGE_SIZE);
            imageView.setFitHeight(getHeightByWidth(imageView, IMAGE_SIZE));
        } else {
            imageView.setFitWidth(getWidthByHeight(imageView, IMAGE_SIZE));
            imageView.setFitHeight(IMAGE_SIZE);
        }

        imageView.setOnMouseClicked(event -> {
            handleImageViewClick(imageContainer);
        });

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(imageView);
        BorderPane.setAlignment(imageView, Pos.CENTER);
        borderPane.setMaxHeight(IMAGE_SIZE);
        borderPane.setMaxWidth(IMAGE_SIZE);
        borderPane.setMinHeight(IMAGE_SIZE);
        borderPane.setMinWidth(IMAGE_SIZE);

        gridPane.add(borderPane, getColumnByIndexAndColumnNum(index, MAX_NUM_OF_COLUMNS),
                getRowByIndexAndColumnNum(index, MAX_NUM_OF_COLUMNS));

        GridPane.setHalignment(imageView, HPos.CENTER);
        GridPane.setValignment(imageView, VPos.CENTER);

    }

    void handleImageViewClick(ImageContainer imageContainer) {
        selectionListener.execute(imageContainer);
        FxUtils.closeWindowThatContains(scrollPane);
    }

    int getScrollPaneWidth() {
        return FRAME_SIZE * 2 + getColumnNum() * IMAGE_SIZE + (getColumnNum() - 1) * GRID_GAP + 20;
    }

    private int getColumnNum() {
        if (getNumOfCells() >= MAX_NUM_OF_COLUMNS)
            return MAX_NUM_OF_COLUMNS;
        return getNumOfCells();
    }

    int getScrollPaneHeight() {
        return FRAME_SIZE * 2 + getVisibleRowNum() * IMAGE_SIZE + (getVisibleRowNum() - 1) * GRID_GAP + 20;
    }

    private int getVisibleRowNum() {
        if (getRowNum() >= MAX_NUM_OF_VISIBLE_ROWS)
            return MAX_NUM_OF_VISIBLE_ROWS;
        return getRowNum();
    }

    private int getRowNum() {
        return (int) Math.ceil(getNumOfCells() * 1.0 / MAX_NUM_OF_COLUMNS);
    }

    int getNumOfCells() {
        return imageContainers.size();
    }

    public int getWindowWidth() {
        return getScrollPaneWidth() + 20;
    }

    public int getWindowHeight() {
        return getScrollPaneHeight() + 50;
    }
}
