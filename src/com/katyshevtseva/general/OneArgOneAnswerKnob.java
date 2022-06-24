package com.katyshevtseva.general;

@FunctionalInterface
public interface OneArgOneAnswerKnob<T, E> {
    E execute(T t);
}
