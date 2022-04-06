package com.katyshevtseva.history;

public interface Action<E extends HasHistory<?>> {

    String getDateString();

    void setDescription(String comment);

    String getDescription();

    void setEntity(E entity);

    long getOrder();
}
