package com.katyshevtseva.general;

public class Utils {

    public static int getRowByIndexAndColumnNum(int index, int columnNum) {
        return index / columnNum;
    }

    public static int getColumnByIndexAndColumnNum(int index, int columnNum) {
        return index % columnNum;
    }
}
