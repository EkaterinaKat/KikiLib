package com.katyshevtseva.hibernate;

import lombok.Getter;

@Getter
public class PageableQuery<T> extends Query<T> {
    private final Integer pageNum;
    private final Integer pageSize;

    public PageableQuery(Class<T> tClass, int pageNum, int pageSize) {
        super(tClass);
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }
}
