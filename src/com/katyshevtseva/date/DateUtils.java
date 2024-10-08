package com.katyshevtseva.date;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtils {
    public static DateFormat READABLE_DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
    public static DateFormat YEAR_FORMAT = new SimpleDateFormat("yyyy");
    public static DateFormat MONTH_FORMAT = new SimpleDateFormat("MMM");
    public static DateFormat READABLE_TIME_FORMAT = new SimpleDateFormat("HH:mm");
    public static DateFormat READABLE_DATE_TIME_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    public static DateFormat MONTH_YEAR_DATE_FORMAT = new SimpleDateFormat("MMM yyyy", new Locale("ru"));
    public static DateFormat DAY_MONTH_DATE_FORMAT = new SimpleDateFormat("dd MMM", new Locale("ru"));

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

    public static boolean before(Date date1, Date date2) {
        return removeTimeFromDate(date1).before(removeTimeFromDate(date2));
    }

    public static boolean after(Date date1, Date date2) {
        return removeTimeFromDate(date1).after(removeTimeFromDate(date2));
    }

    public static boolean equalsIgnoreTime(Date date1, Date date2) {
        return removeTimeFromDate(date1).equals(removeTimeFromDate(date2));
    }

    public static boolean dateBelongsToPeriod(Period period, Date date) {
        return !before(date, period.start()) && !after(date, period.end());
    }

    public static Date removeTimeFromDate(Date date) {
        try {
            return READABLE_DATE_FORMAT.parse(READABLE_DATE_FORMAT.format(date));
        } catch (ParseException e) {
            throw new RuntimeException();
        }
    }

    public static Period getLastMonthPeriod() {
        return new Period(shiftDate(new Date(), TimeUnit.MONTH, -1), new Date());
    }

    public static Period getPrevMonthPeriod() {
        return getPeriodOfMonthDateBelongsTo(shiftDate(new Date(), TimeUnit.MONTH, -1));
    }

    public static Period getCurrentMonthPeriod() {
        return getPeriodOfMonthDateBelongsTo(new Date());
    }

    public static Date getNextMonthFirstDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DATE, 1);
        return calendar.getTime();
    }

    public static int getWeekdayIndex(Date date) {
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_WEEK);
    }

    ////////////////////////////// Get number of some time units between dates //////////////////////////////

    public static int getNumberOfMinutes(Date startDate, Date finishDate) {
        long diff = finishDate.getTime() - startDate.getTime();
        return (int) (diff / 60000);
    }

    public static int getNumberOfDays(Period period) {
        return getNumberOfDays(period.start(), period.end());
    }

    public static int getNumberOfDaysInclBounds(Period period) {
        return getNumberOfDays(period) + 1;
    }

    /**
     * @return Число дней между датами. Если даты соседние, то число дней = 1.
     * Если даты совпадают, то число дней = 0.
     * Если startDate раньше finishDate, то число положительное и наоборот.
     */
    public static int getNumberOfDays(Date startDate, Date finishDate) {
        long diff = finishDate.getTime() - startDate.getTime();
        System.out.println(diff);
        return (int) (diff / 86_400_000);
    }

    ////////////////////////////// Shift date //////////////////////////////

    public static Date shiftDate(Date date, TimeUnit unit, int numOfUnits) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(unit.getIntRepresentationForCalendar(), numOfUnits);
        return calendar.getTime();
    }

    public static String getWeekdayName(Date date) {
        DateFormatSymbols symbols = new DateFormatSymbols();
        String[] dayNames = symbols.getShortWeekdays();
        return dayNames[getWeekdayIndex(date)];
    }

    ////////////////////////////// String representation //////////////////////////////

    public static String getStringRepresentationOfPeriod(Date start, Date end) {
        return String.format("%s-%s",
                start != null ? READABLE_DATE_FORMAT.format(start) : "*",
                end != null ? READABLE_DATE_FORMAT.format(end) : "*");
    }

    public static String getPeriodStringWithLengthIncludingBorders(Period period) {
        return getStringRepresentationOfPeriod(period) + " (" + (getNumberOfDays(period) + 1) + ")";
    }

    public static String getStringRepresentationOfPeriod(Period period) {
        return getStringRepresentationOfPeriod(period.start(), period.end());
    }

    public static Integer getYearDateBelongsTo(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    ////////////////////////////// Get year/month/week date belongs to //////////////////////////////

    public static Period getPeriodOfYearDateBelongsTo(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        Date start = calendar.getTime();
        Date end = shiftDate(shiftDate(start, TimeUnit.YEAR, 1), TimeUnit.DAY, -1);
        return new Period(start, end);
    }

    public enum TimeUnit {
        DAY(Calendar.DATE), MONTH(Calendar.MONTH), YEAR(Calendar.YEAR);

        private final int intRepresentationForCalendar;

        TimeUnit(int intRepresentationForCalendar) {
            this.intRepresentationForCalendar = intRepresentationForCalendar;
        }

        public int getIntRepresentationForCalendar() {
            return intRepresentationForCalendar;
        }
    }

    public static Period getPeriodOfMonthDateBelongsTo(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, 1);
        Date start = calendar.getTime();
        Date end = shiftDate(shiftDate(start, TimeUnit.MONTH, 1), TimeUnit.DAY, -1);
        return new Period(start, end);
    }

    public static Period getPeriodOfWeekDateBelongsTo(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            calendar.add(Calendar.DATE, -1);
        }
        Date start = calendar.getTime();
        Date end = shiftDate(start, TimeUnit.DAY, 6);
        return new Period(start, end);
    }
}
