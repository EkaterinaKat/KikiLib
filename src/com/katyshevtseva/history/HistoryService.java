package com.katyshevtseva.history;

import java.util.Date;

public abstract class HistoryService<A extends Action> {
    protected abstract A createNewAction();

    protected abstract void saveNewAction(A action);

    public void createNewAction(String description) {
        A action = createNewAction();
        action.setDate(new Date());
        action.setDescription(description);
        saveNewAction(action);
    }

    public static String getStatusChangeDesc(String oldStatus, String newStatus) {
        return String.format("%s -> %s", oldStatus, newStatus);
    }
}
