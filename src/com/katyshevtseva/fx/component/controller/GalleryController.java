package com.katyshevtseva.fx.component.controller;

import com.katyshevtseva.fx.ImageContainer;
import com.katyshevtseva.fx.Size;
import com.katyshevtseva.fx.WindowBuilder.FxController;
import com.katyshevtseva.general.OneArgKnob;
import com.katyshevtseva.general.OneArgOneAnswerKnob;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

import static com.katyshevtseva.fx.FxUtils.getPaneWithHeight;
import static com.katyshevtseva.fx.FxUtils.getPaneWithWidth;
import static com.katyshevtseva.fx.ImageSizeUtil.placeImageInSquare;
import static com.katyshevtseva.general.GeneralUtils.getColumnByIndexAndColumnNum;
import static com.katyshevtseva.general.GeneralUtils.getRowByIndexAndColumnNum;

public class GalleryController implements FxController {
    private static final int FRAME_SIZE = 20;
    private static final int GAP_SIZE = 10;
    private final int IMAGE_SIZE;
    private final Size scrollPaneSize;
    private final int columnNum;
    private List<ImageContainer> imageContainers;
    private final OneArgKnob<ImageContainer> clickHandler;
    private OneArgOneAnswerKnob<ImageContainer, ImageView> iconSupplier;
    @FXML
    private VBox root;
    private GridPane gridPane;

    public GalleryController(Size componentSize,
                             int columnNum,
                             List<ImageContainer> imageContainers,
                             OneArgKnob<ImageContainer> clickHandler) {
        this.scrollPaneSize = componentSize;
        this.columnNum = columnNum;
        this.imageContainers = imageContainers;
        this.clickHandler = clickHandler;

        IMAGE_SIZE = (scrollPaneSize.getWidth() - 30 - FRAME_SIZE * 2 - GAP_SIZE * (columnNum - 1)) / columnNum;
    }

    @FXML
    private void initialize() {
        initializePanes();
        fillGridPane();
    }

    public void setImageContainers(List<ImageContainer> imageContainers) {
        this.imageContainers = imageContainers;
        fillGridPane();
    }

    public void setIconSupplier(OneArgOneAnswerKnob<ImageContainer, ImageView> iconSupplier) {
        this.iconSupplier = iconSupplier;
    }

    private void fillGridPane() {
        gridPane.getChildren().clear();

        for (int i = 0; i < imageContainers.size(); i++) {
            addImageToGridPane(i);
        }
    }

    private void addImageToGridPane(int index) {
        ImageContainer imageContainer = imageContainers.get(index);
        ImageView imageView = new ImageView(imageContainer.getImage());

        if (clickHandler != null) {
            imageView.setOnMouseClicked(event -> clickHandler.execute(imageContainer));
        }

        ImageView icon = iconSupplier != null ? iconSupplier.execute(imageContainer) : null;
        gridPane.add(placeImageInSquare(imageView, IMAGE_SIZE, icon),
                getColumnByIndexAndColumnNum(index, columnNum),
                getRowByIndexAndColumnNum(index, columnNum));

        GridPane.setHalignment(imageView, HPos.CENTER);
        GridPane.setValignment(imageView, VPos.CENTER);
    }

    private void initializePanes() {
        gridPane = new GridPane();
        gridPane.setVgap(GAP_SIZE);
        gridPane.setHgap(GAP_SIZE);
        HBox hBox = new HBox();
        hBox.getChildren().addAll(getPaneWithWidth(FRAME_SIZE), gridPane, getPaneWithWidth(FRAME_SIZE));
        VBox vBox = new VBox();
        vBox.getChildren().addAll(getPaneWithHeight(FRAME_SIZE), hBox, getPaneWithHeight(FRAME_SIZE));
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(vBox);
        root.getChildren().add(scrollPane);
        root.setMinHeight(scrollPaneSize.getHeight());
        root.setMaxHeight(scrollPaneSize.getHeight());
        root.setMinWidth(scrollPaneSize.getWidth());
        root.setMaxWidth(scrollPaneSize.getWidth());
    }
}
