package com.katyshevtseva.fx.component;

import com.katyshevtseva.fx.ImageContainer;
import com.katyshevtseva.fx.Size;
import com.katyshevtseva.fx.WindowBuilder;
import com.katyshevtseva.fx.component.controller.GalleryController;
import com.katyshevtseva.fx.component.controller.MultipleChoiceController;
import com.katyshevtseva.general.OneArgKnob;
import javafx.scene.Node;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

public class ComponentBuilder {
    private final String COMPONENT_FXML_LOCATION = "/com/katyshevtseva/fx/component/fxml/";
    private Size size = new Size(150, 240);

    public ComponentBuilder setSize(Size size) {
        this.size = size;
        return this;
    }

    public <E> Component<MultipleChoiceController<E>> getMultipleChoiceComponent(List<E> items) {
        MultipleChoiceController<E> controller = new MultipleChoiceController<>(items, size);
        WindowBuilder windowBuilder = new WindowBuilder(COMPONENT_FXML_LOCATION + "multiple_choice.fxml")
                .setController(controller)
                .setWidth(size.getWidth())
                .setHeight(size.getHeight());
        Node node = windowBuilder.getNode();
        return new Component<>(controller, node);
    }

    /**
     * @param imageContainers должны содержать абсолютные пути до изображений
     */
    public Component<GalleryController> getGalleryComponent(int columnNum,
                                                            List<ImageContainer> imageContainers,
                                                            OneArgKnob<ImageContainer> clickHandler) {
        GalleryController galleryController = new GalleryController(size, columnNum, imageContainers, clickHandler);
        WindowBuilder windowBuilder = new WindowBuilder(COMPONENT_FXML_LOCATION + "gallery.fxml")
                .setController(galleryController)
                .setWidth(size.getWidth())
                .setHeight(size.getHeight());
        Node node = windowBuilder.getNode();
        return new Component<>(galleryController, node);
    }

    @Data
    @RequiredArgsConstructor
    public class Component<E> {
        private final E controller;
        private final Node node;
    }
}
