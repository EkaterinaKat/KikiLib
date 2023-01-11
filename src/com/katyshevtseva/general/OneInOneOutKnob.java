package com.katyshevtseva.general;

@FunctionalInterface
public interface OneInOneOutKnob<T, E> {
    E execute(T t);
}
