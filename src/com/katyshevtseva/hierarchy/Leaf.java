package com.katyshevtseva.hierarchy;

public interface Leaf extends HierarchyNode {

    @Override
    default boolean isLeaf() {
        return true;
    }
}
