package com.katyshevtseva.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtils {
    public static DateFormat READABLE_DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
    public static DateFormat READABLE_TIME_FORMAT = new SimpleDateFormat("HH:mm");
    public static DateFormat READABLE_DATE_TIME_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    public static DateFormat MONTH_YEAR_DATE_FORMAT = new SimpleDateFormat("MMM yyyy", new Locale("ru"));

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

    /**
     * @param period
     * @return Список всех дат, которых принадлежат периоду. Границы включаются
     */
    public static List<Date> getDateRange(Period period) {
        Date date = new Date(period.start().getTime());
        Date oneDayAfterEnd = shiftDate(period.end(), TimeUnit.DAY, 1);

        List<Date> result = new ArrayList<>();
        while (before(date, oneDayAfterEnd)) {
            result.add(date);
            date = shiftDate(date, TimeUnit.DAY, 1);
        }
        return result;
    }

    private static boolean before(Date date1, Date date2) {
        return removeTimeFromDate(date1).before(removeTimeFromDate(date2));
    }

    public static boolean equalsIgnoreTime(Date date1, Date date2) {
        return removeTimeFromDate(date1).equals(removeTimeFromDate(date2));
    }

    public static Date removeTimeFromDate(Date date) {
        try {
            return READABLE_DATE_FORMAT.parse(READABLE_DATE_FORMAT.format(date));
        } catch (ParseException e) {
            throw new RuntimeException();
        }
    }

    public static String getStringRepresentationOfPeriod(Period period) {
        return String.format("%s-%s",
                period.start() != null ? READABLE_DATE_FORMAT.format(period.start()) : "*",
                period.end() != null ? READABLE_DATE_FORMAT.format(period.end()) : "*");
    }

    public static String getPeriodStringWithLengthIncludingBorders(Period period) {
        return getStringRepresentationOfPeriod(period) + " (" + (getNumberOfDays(period) + 1) + ")";
    }

    public static Period getLastMonthPeriod() {
        return new Period(shiftDate(new Date(), TimeUnit.MONTH, -1), new Date());
    }

    public static int getNumberOfMinutes(Date startDate, Date finishDate) {
        long diff = finishDate.getTime() - startDate.getTime();
        return (int) (diff / 60000);
    }

    public static int getNumberOfDays(Period period) {
        return getNumberOfDays(period.start(), period.end());
    }

    /**
     * @return Число дней между датами. Если даты соседние, то число дней = 1.
     * Если даты совпадают, то число дней = 0.
     * Если startDate раньше finishDate, то число положительное и наоборот.
     */
    public static int getNumberOfDays(Date startDate, Date finishDate) {
        long diff = finishDate.getTime() - startDate.getTime();
        return (int) (diff / 86_400_000);
    }

    public static Date getNextMonthFirstDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DATE, 1);
        return calendar.getTime();
    }

    public static String getMonthYearString(Date date) {
        return MONTH_YEAR_DATE_FORMAT.format(date);
    }
}
