package com.katyshevtseva.history;

import java.util.Date;

public abstract class StatusHistoryServce<S extends Status> {

    protected abstract StatusChangeAction<S> createNewStatusChangeAction();

    protected abstract void saveNewStatusChangeAction(StatusChangeAction<S> action);

    void setStatus(HasStatusHistory<S> hasStatusHistory, S status, String description) {
        StatusChangeAction<S> action = createNewStatusChangeAction();
        action.setDate(new Date());
        action.setFrom(hasStatusHistory.getStatus());
        action.setTo(status);
        action.setDescription(description);
        saveNewStatusChangeAction(action);

        hasStatusHistory.setStatus(status);
    }
}
