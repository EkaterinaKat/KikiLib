package com.katyshevtseva.date;

import lombok.Getter;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

@Getter
public enum Month {
    JANUARY(0, "Январь"), FEBRUARY(1, "Февраль"), MARCH(2, "Март"),
    APRIL(3, "Апрель"), MAY(4, "Май"), JUNE(5, "Июнь"),
    JULY(6, "Июль"), AUGUST(7, "Август"), SEPTEMBER(8, "Сентябрь"),
    OCTOBER(9, "Октябрь"), NOVEMBER(10, "Ноябрь"), DECEMBER(11, "Декабрь");

    private final String title;
    private final int index;

    Month(int index, String title) {
        this.title = title;
        this.index = index;
    }

    public static Month findByIndex(int index) {
        return Arrays.stream(Month.values())
                .filter(month -> month.getIndex() == index)
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    public static Month findByDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return Month.findByIndex(calendar.get(Calendar.MONTH));
    }
}
