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

    public static String getSuccessBanner() {
        return getSuccessBanner("");
    }

    public static String getFailedBanner() {
        return getFailedBanner("");
    }

    public static String getHeader(String text) {
        return String.format("\n*************** %s ***************", text);
    }

    public static String getSuccessBanner(String title) {
        return String.format("*************** %s SUCCESS o(>ω<)o ***************\n", title);
    }

    public static String getFailedBanner(String title) {
        return String.format("*************** %s FAILED (」°ロ°)」 ***************\n", title);
    }

    public static String wrapText(String text, int lineLength) {
        StringBuilder result = new StringBuilder();
        int numOfIteration = (int) Math.ceil((text.length() * 1.0) / lineLength);
        for (int i = 0; i < numOfIteration; i++) {
            int start = i * lineLength;
            int end = i * lineLength + lineLength;
            if (end >= text.length())
                result.append(text.substring(start));
            else
                result.append(text, start, end).append("\n");
        }
        return result.toString();
    }

    public static String wrapTextByWords(String input, int lineLength) {
        StringBuilder result = new StringBuilder();
        String[] words = input.split(" ");
        for (String word : words) {
            if (word.length() > lineLength) {
                word = wrapLongWord(word, lineLength, lineLength - getLastLineLength(result.toString()));
            } else if (getLastLineLength(result.toString()) + word.length() + 1 > lineLength) {
                result.append("\n");
            }
            result.append(word).append(" ");
        }
        return result.toString();
    }

    private static int getLastLineLength(String s) {
        String[] lines = s.split("\n");
        return lines[lines.length - 1].length();
    }

    private static String wrapLongWord(String s, int lineLength, int firstLineLength) {
        StringBuilder result = new StringBuilder(s.substring(0, firstLineLength - 1)).append("\n");
        char[] letters = s.substring(firstLineLength).toCharArray();
        for (char letter : letters) {
            if (getLastLineLength(result.toString()) + 1 > lineLength) {
                result.append("\n");
            }
            result.append(letter);
        }
        return result.toString();
    }
}
