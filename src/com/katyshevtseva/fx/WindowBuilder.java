package com.katyshevtseva.fx;

import com.katyshevtseva.config.ConfigConstants;
import com.katyshevtseva.config.ConfigUtil;
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
    private final String fxmlName;
    private final String iconImagePath;
    private final String cssPath;
    private String title = "";
    private Size size = new Size(500, 500);
    private FxController controller;
    private boolean isModal = false;
    private boolean stretchable = false;
    private EventHandler<WindowEvent> eventHandler;
    private String cursorImagePath;

    public static Node getNode(NodeInfo nodeInfo, FxController controller) {
        return new WindowBuilder(nodeInfo.getFullFileName()).setController(controller).getNode();
    }

    public interface NodeInfo {
        String getFullFileName();
    }

    public static void openDialog(DialogInfo dialogInfo, FxController controller) {
        new WindowBuilder(dialogInfo.getFullFileName())
                .setController(controller).setSize(dialogInfo.getSize())
                .setTitle(dialogInfo.getTitle()).showWindow();
    }

    public interface DialogInfo {
        String getFullFileName();

        String getTitle();

        Size getSize();
    }

    public WindowBuilder(String fxmlName) {
        this.fxmlName = fxmlName;

        ConfigUtil configUtil = new ConfigUtil();
        iconImagePath = configUtil.getConfigOrNull(ConfigConstants.ICON_IMAGE_PATH);
        cssPath = configUtil.getConfigOrNull(ConfigConstants.CSS_PATH);
    }

    public WindowBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public WindowBuilder setSize(Size size) {
        this.size = size;
        return this;
    }

    public WindowBuilder setSize(int height, int width) {
        this.size = new Size(height, width);
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
            stage.setMinHeight(size.getHeight());
            stage.setMaxHeight(size.getHeight());
            stage.setMinWidth(size.getWidth());
            stage.setMaxWidth(size.getWidth());
        }
        Scene scene = new Scene(getNodeByFxmlAndController(), size.getWidth(), size.getHeight());
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
