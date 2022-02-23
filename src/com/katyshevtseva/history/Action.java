package com.katyshevtseva.history;

import java.util.Date;

public interface Action<E extends HasHistory<?>> {

    Date getDate();

    void setDescription(String comment);

    String getDescription();

    void setEntity(E entity);
}
