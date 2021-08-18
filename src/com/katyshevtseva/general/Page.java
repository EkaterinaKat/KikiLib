package com.katyshevtseva.general;

import lombok.Getter;

import java.util.List;

public class Page<T> {
    @Getter
    private final int pageNum;
    @Getter
    private final int totalPages;
    @Getter
    List<T> content;

    public Page(List<T> content, int pageNum, int totalPages) {
        this.pageNum = pageNum;
        this.totalPages = totalPages;
        this.content = content;
    }
}
