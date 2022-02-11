package com.katyshevtseva.hierarchy;

public interface Group extends HierarchyNode {

    @Override
    default boolean isLeaf() {
        return false;
    }
}
