package com.katyshevtseva.history;

import com.katyshevtseva.general.GeneralUtils;

import java.util.List;

public abstract class StatusHistoryValidator<A extends Action> {

    protected abstract List<HasHistory<A>> getAllEntitiesToValidate();

    protected void validate(String validationTitle) {
        for (HasHistory<A> entity : getAllEntitiesToValidate()) {
            checkPresenceOfInitAction(entity, validationTitle);
        }
        System.out.println(GeneralUtils.getSuccessBanner(validationTitle));
    }

    private void checkPresenceOfInitAction(HasHistory<A> entity, String validationTitle) {
        if (entity.getActions().size() == 0) {
            System.out.println(GeneralUtils.getFailedBanner(validationTitle));
            throw new RuntimeException(entity.getTitle() + " don't have init action");
        }
    }
}
