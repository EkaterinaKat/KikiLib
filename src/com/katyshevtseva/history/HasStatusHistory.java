package com.katyshevtseva.history;

public interface HasStatusHistory<S extends Status, A extends Action> extends HasHistory<A> {

    S getStatus();
}
