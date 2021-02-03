package com.katyshevtseva.date;

import java.util.Date;

public class Period {
    private Date startDate;
    private Date endDate;

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
}
