package com.katyshevtseva.general;

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
}
