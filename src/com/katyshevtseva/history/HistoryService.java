package com.katyshevtseva.history;

import java.util.Date;

public abstract class HistoryService<E extends HasHistory<A>, A extends Action<E>> {
    protected abstract A createNewAction();

    protected abstract void saveNewAction(A action);

    protected abstract void setDate(A action, Date date);

    public void createNewAction(E entity, String description) {
        A action = createNewAction();
        action.setEntity(entity);
        setDate(action, new Date());
        action.setDescription(description);
        saveNewAction(action);
    }

    public static String getStatusChangeDesc(String oldStatus, String newStatus) {
        return String.format("%s -> %s", oldStatus, newStatus);
    }
}
