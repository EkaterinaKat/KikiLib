package com.katyshevtseva.history;

public interface HasStatusHistory<S extends Status> extends HasHistory<StatusChangeAction<S>> {

    S getStatus();

    void setStatus(S status);
}
