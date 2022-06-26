package com.katyshevtseva.fx.component;

import com.katyshevtseva.fx.ImageContainer;
import com.katyshevtseva.fx.Size;
import com.katyshevtseva.fx.WindowBuilder;
import com.katyshevtseva.fx.WindowBuilder.FxController;
import com.katyshevtseva.fx.component.controller.*;
import com.katyshevtseva.general.OneArgKnob;
import com.katyshevtseva.general.TwoArgKnob;
import com.katyshevtseva.hierarchy.HierarchyNode;
import com.katyshevtseva.hierarchy.HierarchyService;
import com.katyshevtseva.hierarchy.StaticHierarchySchemaLine;
import com.katyshevtseva.history.Action;
import com.katyshevtseva.history.HasHistory;
import javafx.scene.Node;
import javafx.scene.control.Label;
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
        return getComponent("multiple_choice.fxml", controller);
    }

    public Component<GalleryController> getGalleryComponent(int columnNum,
                                                            List<ImageContainer> imageContainers,
                                                            OneArgKnob<ImageContainer> clickHandler) {
        GalleryController controller = new GalleryController(size, columnNum, imageContainers, clickHandler);
        return getComponent("gallery.fxml", controller);
    }

    public Component<HierarchyController> getHierarchyComponent(HierarchyService service, boolean editable, boolean groupTable) {
        return getHierarchyComponent(service, editable, groupTable, null);
    }

    public Component<HierarchyController> getHierarchyComponent(HierarchyService service, boolean editable, boolean groupTable, TwoArgKnob<HierarchyNode, Label> nodeLabelAdjuster) {
        HierarchyController controller = new HierarchyController(service, editable, groupTable, size, nodeLabelAdjuster);
        return getComponent("hierarchy.fxml", controller);
    }

    public Component<StaticHierarchyController> getStaticHierarchyComponent(
            List<StaticHierarchySchemaLine> schema,
            TwoArgKnob<StaticHierarchySchemaLine, Label> nodeLabelAdjuster,
            OneArgKnob<StaticHierarchySchemaLine> clickHandler) {
        StaticHierarchyController controller = new StaticHierarchyController(schema, size, nodeLabelAdjuster, clickHandler);
        return getComponent("hierarchy.fxml", controller);
    }

    public <E extends HasHistory<A>, A extends Action<E>> Component<HistoryController<E, A>> getHistoryComponent() {
        HistoryController<E, A> controller = new HistoryController<>(size);
        return getComponent("history.fxml", controller);
    }

    public <T> Component<PaginationPaneController<T>> getPaginationComponent(
            PaginationPaneController.PageSource<T> pageSource,
            OneArgKnob<List<T>> contentReceiver) {
        PaginationPaneController<T> controller = new PaginationPaneController<>(pageSource, contentReceiver);
        return getComponent("pagination_pane.fxml", controller);
    }

    private <Controller extends FxController> Component<Controller> getComponent(String fxml, Controller controller) {
        WindowBuilder windowBuilder = new WindowBuilder(COMPONENT_FXML_LOCATION + fxml)
                .setController(controller)
                .setSize(size);
        return new Component<>(controller, windowBuilder.getNode());
    }

    @Data
    @RequiredArgsConstructor
    public class Component<E> {
        private final E controller;
        private final Node node;
    }
}
