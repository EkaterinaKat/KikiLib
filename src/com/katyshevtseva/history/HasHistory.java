package com.katyshevtseva.history;

import java.util.List;

public interface HasHistory<A extends Action<?>> {

    List<A> getHistory();

    String getConditionDescForHistory();
}
