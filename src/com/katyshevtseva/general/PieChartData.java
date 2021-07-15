package com.katyshevtseva.general;

import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PieChartData {
    private TitleType titleType;
    private List<Segment> segments = new ArrayList<>();
    @Getter
    private long total = 0;
    @Getter
    private String title;

    enum TitleType {
        ARBITRARY, TOTAL_STRING
    }

    public PieChartData(String title) {
        this.title = title;
        titleType = TitleType.ARBITRARY;
    }

    public PieChartData() {
        titleType = TitleType.TOTAL_STRING;
    }

    public List<Segment> getGetSegmentList() {
        return Collections.unmodifiableList(segments);
    }

    public void addSegment(Segment segment) {
        segments.add(segment);
        total += segment.getAmount();
        recalculatePercents();
        if (titleType == TitleType.TOTAL_STRING)
            title = "Total: " + total;
    }

    private void recalculatePercents() {
        for (Segment segment : segments) {
            if (total == 0)
                segment.setPercent(0);
            else
                segment.setPercent((int) ((segment.getAmount() * 100) / total));
            segment.setTitleAmountAndPercentInfo(segment.title + " - " + segment.amount + " - " + segment.percent + "%");
        }
    }

    @Data
    public static class Segment {
        private int amount;
        private int percent;
        private String titleAmountAndPercentInfo;
        private String title;

        public Segment(int amount, String title) {
            this.amount = amount;
            this.title = title;
        }
    }
}
