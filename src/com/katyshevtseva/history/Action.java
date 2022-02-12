package com.katyshevtseva.history;

import java.util.Date;

public interface Action {

    void setDate(Date date);

    Date getDate();

    void setDescription(String comment);

    String getDescription();
}
