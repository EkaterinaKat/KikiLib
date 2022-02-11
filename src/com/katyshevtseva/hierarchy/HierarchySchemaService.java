package com.katyshevtseva.hierarchy;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class HierarchySchemaService {
    private HierarchyService service;
    private boolean editable;

    public List<SchemaLine> getSchema() {
        List<SchemaLine> schema = new ArrayList<>();
        for (HierarchyNode topLevelNode : service.getTopLevelNodes()) {
            schema.addAll(getSchemaByRoot(topLevelNode, 0));
            schema.add(new EmptyLine());
        }
        return schema;
    }

    private List<SchemaLine> getSchemaByRoot(HierarchyNode node, int level) {
        List<SchemaLine> schema = new ArrayList<>();
        schema.add(new Entry(node, level));

        if (node.isLeaf())
            return schema;

        for (HierarchyNode childNode : service.getNodesByParent(node)) {
            schema.addAll(getSchemaByRoot(childNode, level + 1));
        }

        if (editable) {
            schema.add(new AddButton(level + 1, (Group) node));
        }

        return schema;
    }

    @Getter
    @AllArgsConstructor
    public class Entry implements SchemaLine {
        private final HierarchyNode node;
        private final int level;

        public void deleteFromSchema() {
            node.setParentGroup(null);
            service.saveModifiedNode(node);
        }

        public boolean isLeaf() {
            return node.isLeaf();
        }

        public boolean isTopLevel() {
            return node.getParentGroup() == null;
        }
    }

    public class AddButton implements SchemaLine {
        @Getter
        private final int level;
        private final Group groupToAddTo;

        AddButton(int level, Group groupToAddTo) {
            this.level = level;
            this.groupToAddTo = groupToAddTo;
        }

        public void add(HierarchyNode childNodeToAdd) throws SchemaException {
            if (childNodeToAdd.getParentGroup() != null)
                throw new SchemaException(childNodeToAdd.getTitle() + " уже имеет родителя");

            if (service.treeWithRootContainsNode(childNodeToAdd, groupToAddTo))
                throw new SchemaException(groupToAddTo.getTitle() + " является подкатегорией категории " + childNodeToAdd.getTitle());

            childNodeToAdd.setParentGroup(groupToAddTo);
            service.saveModifiedNode(childNodeToAdd);
        }
    }

    public static class EmptyLine implements SchemaLine {

    }

    public interface SchemaLine {

    }
}
