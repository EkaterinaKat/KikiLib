package com.katyshevtseva.general;

import java.util.List;

public class GeneralUtils {

    public static int getRowByIndexAndColumnNum(int index, int columnNum) {
        return index / columnNum;
    }

    public static int getColumnByIndexAndColumnNum(int index, int columnNum) {
        return index % columnNum;
    }

    public static boolean isEmpty(String s) {
        return s == null || s.equals("");
    }

    public static boolean isEmpty(List list) {
        return list == null || list.isEmpty();
    }

    public static String getSuccessBanner(String title) {
        return String.format("***********************************************\n" +
                "                 %s SUCCESS\n" +
                "***********************************************\n", title);
    }

    public static String getFailedBanner(String title) {
        return String.format("***********************************************\n" +
                "                 %s FAILED\n" +
                "***********************************************\n", title);
    }
}
