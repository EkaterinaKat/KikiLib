package com.katyshevtseva.hierarchy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public abstract class HierarchyService<L extends Leaf, G extends Group> {
    private HierarchySchemaService schemaService;

    protected abstract void createNewGroup(String name);

    protected abstract void saveModifiedGroup(G group);

    protected abstract void saveModifiedLeaf(L leaf);

    public abstract List<G> getAllGroups();

    protected abstract List<L> getAllLeaves();

    protected abstract void deleteGroup(G group);

    /**
     * Должен возвращать листья из первого поколения потомков
     */
    protected abstract List<L> getLeavesByParentGroup(G group);

    /**
     * Должен возвращать группы из первого поколения потомков
     */
    protected abstract List<G> getGroupsByParentGroup(G group);

    public List<HierarchySchemaService.SchemaLine> getSchema() {
        if (schemaService == null) {
            schemaService = new HierarchySchemaService(this);
        }
        return schemaService.getSchema();
    }

    public void createGroup(String name) {
        createNewGroup(name);
    }

    public void deleteFromSchema(HierarchyNode node) {
        node.setParentGroup(null);
        saveModifiedNode(node);
    }

    public void destroyTreeAndDeleteGroup(G group) {
        for (HierarchyNode childNode : getNodesByParent(group)) {
            childNode.setParentGroup(null);
            saveModifiedNode(childNode);
        }

        deleteGroup(group);
    }

    protected void saveModifiedNode(HierarchyNode node) {
        if (node.isLeaf())
            saveModifiedLeaf((L) node);
        else
            saveModifiedGroup((G) node);
    }

    /**
     * Возвращает всех потомков (и листья и группы) parentNode в первом поколении. Сам parentNode в список не включается
     */
    public List<HierarchyNode> getNodesByParent(HierarchyNode parentNode) {
        List<HierarchyNode> nodes = new ArrayList<>();
        if (!parentNode.isLeaf()) {
            nodes.addAll(getLeavesByParentGroup((G) parentNode));
            nodes.addAll(getGroupsByParentGroup((G) parentNode));
        }
        return nodes;
    }

    public List<HierarchyNode> getTopLevelNodes() {
        List<HierarchyNode> nodes = new ArrayList<>();
        nodes.addAll(getTopLevelLeaves());
        nodes.addAll(getTopLevelGroups());
        return nodes;
    }

    public boolean isTopLevel(HierarchyNode node) {
        return node.getParentGroup() == null;
    }

    protected List<L> getTopLevelLeaves() {
        return getAllLeaves().stream().filter(leaf -> leaf.getParentGroup() == null).collect(Collectors.toList());
    }

    protected List<G> getTopLevelGroups() {
        return getAllGroups().stream().filter(group -> group.getParentGroup() == null).collect(Collectors.toList());
    }

    public List<L> getAllDescendantLeavesByHierarchyNode(HierarchyNode root) {
        if (root.isLeaf())
            return Collections.singletonList((L) root);

        return getNodesByParent(root).stream()
                .flatMap(node -> getAllDescendantLeavesByHierarchyNode(node).stream())
                .collect(Collectors.toList());
    }

    public List<G> getAllDescendantGroupsByParentGroup(G root) {
        List<G> result = getGroupsByParentGroup(root).stream()
                .flatMap(node -> getAllDescendantGroupsByParentGroup(node).stream())
                .collect(Collectors.toList());
        result.add(root);

        return result;
    }

    public boolean treeWithRootContainsNode(HierarchyNode root, HierarchyNode nodeToSearch) {
        if (nodesAreEqual(root, nodeToSearch))
            return true;

        if (root.isLeaf())
            return false;

        for (HierarchyNode childNode : getNodesByParent(root))
            if (treeWithRootContainsNode(childNode, nodeToSearch))
                return true;

        return false;
    }

    private boolean nodesAreEqual(HierarchyNode node1, HierarchyNode node2) {
        return node1.isLeaf() == node2.isLeaf() && node1.getId() == node2.getId();
    }
}
