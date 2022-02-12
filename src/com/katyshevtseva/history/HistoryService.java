package com.katyshevtseva.history;

import java.util.Date;

public abstract class HistoryService<A extends Action> {
    protected abstract A createNewAction();

    protected abstract void saveNewAction(A action);

    void createNewAction(String description) {
        A action = createNewAction();
        action.setDate(new Date());
        action.setDescription(description);
        saveNewAction(action);
    }
}
