package com.katyshevtseva.date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtils {
    public static DateFormat READABLE_DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
    public static DateFormat READABLE_TIME_FORMAT = new SimpleDateFormat("HH:mm");

    public enum TimeUnit {
        DAY(Calendar.DATE), MONTH(Calendar.MONTH), YEAR(Calendar.YEAR);

        private int intRepresentationForCalendar;

        TimeUnit(int intRepresentationForCalendar) {
            this.intRepresentationForCalendar = intRepresentationForCalendar;
        }

        public int getIntRepresentationForCalendar() {
            return intRepresentationForCalendar;
        }
    }

    public static Date shiftDate(Date date, TimeUnit unit, int numOfUnits) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(unit.getIntRepresentationForCalendar(), numOfUnits);
        return calendar.getTime();
    }

    public static List<Date> getDateRange(Period period) {
        Date date = new Date(period.start().getTime());
        Date oneDayAfterEnd = shiftDate(period.end(), TimeUnit.DAY, 1);

        List<Date> result = new ArrayList<>();
        while (date.before(oneDayAfterEnd)) {
            result.add(date);
            date = shiftDate(date, TimeUnit.DAY, 1);
        }
        return result;
    }

    public static String getStringRepresentationOfPeriod(Period period) {
        return String.format("%s-%s", READABLE_DATE_FORMAT.format(period.start()), READABLE_DATE_FORMAT.format(period.end()));
    }

    public static Period getLastMonthPeriod() {
        return new Period(shiftDate(new Date(), TimeUnit.MONTH, -1), new Date());
    }

    public static int getNumberOfMinutes(Date startDate, Date finishDate) {
        long diff = finishDate.getTime() - startDate.getTime();
        return (int) (diff / 60000);
    }
}
