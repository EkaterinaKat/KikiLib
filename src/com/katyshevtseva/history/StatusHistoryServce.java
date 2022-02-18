package com.katyshevtseva.history;

import java.util.Date;

public abstract class StatusHistoryServce<S extends Status, A extends Action> extends HistoryService<A> {

    public void createNewStatusChangeActionAction(HasStatusHistory<S, A> hasStatusHistory, S status) {
        A action = createNewAction();
        action.setDate(new Date());
        action.setDescription(getStatusChangeDesc(hasStatusHistory.getStatus(), status));
        saveNewAction(action);
    }

    private String getStatusChangeDesc(S oldStatus, S newStatus) {
        return String.format("%s -> %s", oldStatus.getTitle(), newStatus.getTitle());
    }
}
