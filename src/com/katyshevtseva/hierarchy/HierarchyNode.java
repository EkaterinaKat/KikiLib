package com.katyshevtseva.hierarchy;

public interface HierarchyNode {

    long getId();

    boolean isLeaf();

    String getTitle();

    void setParentGroup(Group group);

    Group getParentGroup();
}
