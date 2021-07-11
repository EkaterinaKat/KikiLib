package com.katyshevtseva.fx;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class WindowBuilder {
    private String fxmlName;
    private String title = "";
    private int width = 500;
    private int height = 500;
    private FxController controller;
    private boolean isModal = false;
    private String iconImagePath;
    private boolean stretchable = false;
    private EventHandler<WindowEvent> eventHandler;
    private String cssPath;
    private String cursorImagePath;

    public WindowBuilder(String fxmlName) {
        this.fxmlName = fxmlName;

        ConfigUtil configUtil = new ConfigUtil();
        iconImagePath = configUtil.getIconImagePath();
        cssPath = configUtil.getCssPath();
    }

    public WindowBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public WindowBuilder setWidth(int width) {
        this.width = width;
        return this;
    }

    public WindowBuilder setHeight(int height) {
        this.height = height;
        return this;
    }

    public WindowBuilder setController(FxController controller) {
        this.controller = controller;
        return this;
    }

    public WindowBuilder setModal(boolean modal) {
        isModal = modal;
        return this;
    }

    @Deprecated
    public WindowBuilder setIconImagePath(String iconImagePath) {
        this.iconImagePath = iconImagePath;
        return this;
    }

    @Deprecated
    public WindowBuilder setCssPath(String cssPath) {
        this.cssPath = cssPath;
        return this;
    }

    public WindowBuilder setStretchable(boolean stretchable) {
        this.stretchable = stretchable;
        return this;
    }

    public WindowBuilder setOnWindowCloseEventHandler(EventHandler<WindowEvent> eventHandler) {
        this.eventHandler = eventHandler;
        return this;
    }

    public WindowBuilder setCursorImagePath(String path) {
        this.cursorImagePath = path;
        return this;
    }

    public void showWindow() {
        getStage().show();
    }

    public Node getNode() {
        return getNodeByFxmlAndController();
    }

    private Stage getStage() {
        Stage stage = new Stage();
        stage.setTitle(title);
        if (!stretchable) {
            stage.setMinHeight(height);
            stage.setMaxHeight(height);
            stage.setMinWidth(width);
            stage.setMaxWidth(width);
        }
        Scene scene = new Scene(getNodeByFxmlAndController(), width, height);
        if (cursorImagePath != null)
            scene.setCursor(new ImageCursor(new Image(cursorImagePath), 19, 19));
        stage.setScene(scene);
        if (iconImagePath != null)
            stage.getIcons().add(new Image(iconImagePath));
        if (isModal)
            stage.initModality(Modality.APPLICATION_MODAL);
        if (eventHandler != null)
            stage.setOnCloseRequest(eventHandler);
        return stage;
    }

    private Parent getNodeByFxmlAndController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlName));
        if (controller != null)
            fxmlLoader.setController(controller);
        Parent parent = null;
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (cssPath != null) {
            String stylesheet = getClass().getResource(cssPath).toExternalForm();
            parent.getStylesheets().add(stylesheet);
        }
        return parent;
    }

    public interface FxController {

    }
}
