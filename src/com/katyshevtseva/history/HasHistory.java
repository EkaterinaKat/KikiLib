package com.katyshevtseva.history;

import java.util.List;

public interface HasHistory<A extends Action> {

    String getTitle();

    List<A> getActions();
}
