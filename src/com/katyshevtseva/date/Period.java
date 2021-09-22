package com.katyshevtseva.date;

import java.util.Date;

import static com.katyshevtseva.date.DateUtils.getStringRepresentationOfPeriod;

public class Period {
    private final Date startDate;
    private final Date endDate;

    public Period(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Date start() {
        return (Date) startDate.clone();
    }

    public Date end() {
        return (Date) endDate.clone();
    }

    @Override
    public String toString() {
        return getStringRepresentationOfPeriod(this);
    }
}
