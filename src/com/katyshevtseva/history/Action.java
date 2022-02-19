package com.katyshevtseva.history;

import java.util.Date;

public interface Action {

    Date getDate();

    void setDescription(String comment);

    String getDescription();
}
