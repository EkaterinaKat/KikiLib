package com.katyshevtseva.time;

import lombok.Getter;

@Getter
public class Time {
    private final int totalMin;
    private final int hour;
    private final int min;

    public Time(int hour, int min) {
        this.hour = hour;
        this.min = min;
        totalMin = hour * 60 + min;
    }

    public Time(int totalMin) {
        this.totalMin = totalMin;
        hour = totalMin / 60;
        min = totalMin % 60;
    }
}
