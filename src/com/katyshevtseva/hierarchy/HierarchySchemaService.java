package com.katyshevtseva.hierarchy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class HierarchySchemaService {
    private HierarchyService<?, ?> service;

    public List<SchemaLine> getSchema() {
        List<SchemaLine> schema = new ArrayList<>();
        for (HierarchyNode topLevelNode : service.getTopLevelNodes()) {
            schema.addAll(getSchemaByRoot(topLevelNode, 0));
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

        schema.add(new AddButton(level + 1, (Group) node));
        return schema;
    }

    public class Entry extends SchemaLine {
        @Getter
        private final HierarchyNode node;

        public Entry(HierarchyNode node, int level) {
            super(level);
            this.node = node;
        }

        public boolean isLeaf() {
            return node.isLeaf();
        }

        public boolean isTopLevel() {
            return node.getParentGroup() == null;
        }
    }

    public class AddButton extends SchemaLine {
        private final Group groupToAddTo;

        AddButton(int level, Group groupToAddTo) {
            super(level);
            this.groupToAddTo = groupToAddTo;
        }

        public void execute(HierarchyNode childNodeToAdd) throws SchemaException {
            if (childNodeToAdd.getParentGroup() != null)
                throw new SchemaException(childNodeToAdd.getTitle() + " уже имеет родителя");

            if (service.treeWithRootContainsNode(childNodeToAdd, groupToAddTo))
                throw new SchemaException(groupToAddTo.getTitle() + " является подкатегорией категории " + childNodeToAdd.getTitle());

            childNodeToAdd.setParentGroup(groupToAddTo);
            service.saveModifiedNode(childNodeToAdd);
        }
    }

    @RequiredArgsConstructor
    public abstract class SchemaLine {
        @Getter
        final int level;
    }
}
