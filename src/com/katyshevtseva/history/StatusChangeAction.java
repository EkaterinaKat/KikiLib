package com.katyshevtseva.history;

public interface StatusChangeAction<S extends Status> extends Action {

    void setFrom(S s);

    void setTo(S s);

    S getFrom();

    S getTo();
}
