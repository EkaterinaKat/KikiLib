package com.katyshevtseva.history;

import java.util.Date;

public abstract class HistoryService<E extends HasHistory<A>, A extends Action<E>> {
    protected abstract A createNewAction();

    protected abstract void saveNewAction(A action);

    protected abstract void setDate(A action, Date date);

    public void commitChangeStatusAction(E entity, Object oldStatus, Object newStatus, String comment) {
        String desc = getStatusChangeDesc(oldStatus, newStatus) + (comment != null ? "\n" + comment : "");
        commitAction(entity, desc);
    }

    public void commitChangeStatusAction(E entity, Object oldStatus, Object newStatus) {
        commitAction(entity, getStatusChangeDesc(oldStatus, newStatus));
    }

    public void commitCreateAction(E entity) {
        commitAction(entity, "CREATED:\n" + entity.getConditionDescForHistory());
    }

    public void commitEditedAction(E entity) {
        commitAction(entity, "EDITED:\n" + entity.getConditionDescForHistory());
    }

    public void commitAction(E entity, String description) {
        commitAction(entity, description, new Date());
    }

    public void commitAction(E entity, String description, Date date) {
        A action = createNewAction();
        action.setEntity(entity);
        setDate(action, date);
        action.setDescription(description);
        saveNewAction(action);
    }

    private static String getStatusChangeDesc(Object oldStatus, Object newStatus) {
        return String.format("%s -> %s", oldStatus, newStatus);
    }
}
