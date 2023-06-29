package com.katyshevtseva.hibernate;

import lombok.Getter;
import org.hibernate.criterion.Order;

import java.util.HashMap;
import java.util.Map;

@Getter
public class PageableQuery<T> {
    private final Map<String, Object> equalityRestrictions = new HashMap<>();
    private final Class<T> tClass;
    private Order order;
    private final Integer pageNum;
    private final Integer pageSize;

    public PageableQuery(Class<T> tClass, int pageNum, int pageSize) {
        this.tClass = tClass;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public PageableQuery<T> addEqualityRestriction(String propertyName, Object propertyValue) {
        equalityRestrictions.put(propertyName, propertyValue);
        return this;
    }

    public PageableQuery<T> setOrder(Order order) {
        this.order = order;
        return this;
    }
}
