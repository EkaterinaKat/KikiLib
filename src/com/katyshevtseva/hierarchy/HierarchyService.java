package com.katyshevtseva.hierarchy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public abstract class HierarchyService {

    protected abstract void createNewGroup(String name);

    protected abstract void saveModifiedGroup(Group group);

    protected abstract void saveModifiedLeaf(Leaf leaf);

    protected abstract List<Group> getAllGroups();

    protected abstract List<Leaf> getAllLeaves();

    protected abstract void deleteGroup(Group group);

    protected abstract List<Leaf> getLeavesByParentGroup(Group group);

    protected abstract List<Group> getGroupsByParentGroup(Group group);

    public void destroyTreeAndDeleteGroup(Group group) {
        destroyTreeWithRootNode(group);
        deleteGroup(group);
    }

    void destroyTreeWithRootNode(HierarchyNode node) {
        node.setParentGroup(null);
        saveModifiedNode(node);

        if (node.isLeaf())
            return;

        for (HierarchyNode childNode : getNodesByParent(node))
            destroyTreeWithRootNode(childNode);
    }

    void saveModifiedNode(HierarchyNode node) {
        if (node.isLeaf())
            saveModifiedLeaf((Leaf) node);
        else
            saveModifiedGroup((Group) node);
    }

    public List<HierarchyNode> getNodesByParent(HierarchyNode parentNode) {
        List<HierarchyNode> nodes = new ArrayList<>();
        if (!parentNode.isLeaf()) {
            nodes.addAll(getLeavesByParentGroup((Group) parentNode));
            nodes.addAll(getGroupsByParentGroup((Group) parentNode));
        }
        return nodes;
    }

    public List<HierarchyNode> getTopLevelNodes() {
        List<HierarchyNode> nodes = new ArrayList<>();
        nodes.addAll(getTopLevelLeaves());
        nodes.addAll(getTopLevelGroups());
        return nodes;
    }

    private List<Leaf> getTopLevelLeaves() {
        return getAllLeaves().stream().filter(leaf -> leaf.getParentGroup() == null).collect(Collectors.toList());
    }

    private List<Group> getTopLevelGroups() {
        return getAllGroups().stream().filter(group -> group.getParentGroup() == null).collect(Collectors.toList());
    }

    public List<Leaf> getAllDescendantLeavesByHierarchyNode(HierarchyNode root) {
        if (root.isLeaf())
            return Collections.singletonList((Leaf) root);

        return getNodesByParent(root).stream()
                .flatMap(node -> getAllDescendantLeavesByHierarchyNode(node).stream())
                .collect(Collectors.toList());
    }

    boolean treeWithRootContainsNode(HierarchyNode root, HierarchyNode nodeToSearch) {
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
