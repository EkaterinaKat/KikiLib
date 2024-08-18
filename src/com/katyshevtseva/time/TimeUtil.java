package com.katyshevtseva.time;


import javafx.scene.control.TextField;

import static com.katyshevtseva.general.GeneralUtils.isEmpty;

public class TimeUtil {

    public static String getTimeStringByMinutes(int totalMins) {
        return getTimeStringByMinutes(totalMins, false);
    }

    public static String getTimeStringByMinutes(int totalMins, boolean shortenRoundHours) {
        Time time = new Time(totalMins);
        if (time.getMin() == 0 && shortenRoundHours)
            return time.getHour() + "";
        return String.format("%d:%02d", time.getHour(), time.getMin());
    }

    public static void setTime(int totalMin, TextField hourTF, TextField minTF) {
        Time time = new Time(totalMin);
        hourTF.setText(time.getHour() + "");
        minTF.setText(time.getMin() + "");
    }

    public static int getTotalMin(TextField hourTF, TextField minTF) {
        String hourStr = hourTF.getText();
        String minStr = minTF.getText();
        Time time = new Time(
                isEmpty(hourStr) ? 0 : Integer.parseInt(hourStr),
                isEmpty(minStr) ? 0 : Integer.parseInt(minStr)
        );
        return time.getTotalMin();
    }
}
