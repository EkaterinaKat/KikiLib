package com.katyshevtseva.general;

@FunctionalInterface
public interface TwoArgKnob<T,E> {
    void execute(T t, E e);
}
