package com.katyshevtseva.general;

@FunctionalInterface
public interface TwoInOneOutKnob<T, E, P> {
    P execute(T t, E e);
}
