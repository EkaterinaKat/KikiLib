package com.katyshevtseva.general;

@FunctionalInterface
public interface OneArgKnob<T> {
    void execute(T t);
}
