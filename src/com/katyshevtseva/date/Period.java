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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Period period = (Period) o;

        if (!startDate.equals(period.startDate)) return false;
        return endDate.equals(period.endDate);
    }

    @Override
    public int hashCode() {
        int result = startDate.hashCode();
        result = 31 * result + endDate.hashCode();
        return result;
    }
}
