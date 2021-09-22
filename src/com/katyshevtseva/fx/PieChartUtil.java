package com.katyshevtseva.fx;

import com.katyshevtseva.general.PieChartData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.input.MouseEvent;

import java.util.stream.Collectors;

public class PieChartUtil {

    public static void tunePieChart(PieChart chart, PieChartData data) {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                data.getGetSegmentList().stream().map(PieChartUtil::segmentToData).collect(Collectors.toList()));
        chart.setData(pieChartData);
        chart.setTitle(data.getTitle());
    }

    private static PieChart.Data segmentToData(PieChartData.Segment segment) {
        PieChart.Data data = new PieChart.Data(segment.getTitle(), segment.getAmount());
        if (segment.getClickHandler() != null) {
            data.getNode().addEventFilter(MouseEvent.MOUSE_PRESSED, event -> segment.getClickHandler().execute());
        }
        return data;
    }

}
