package com.katyshevtseva.hibernate;

import lombok.Getter;
import org.hibernate.criterion.Order;

import java.util.HashMap;
import java.util.Map;

@Getter
public class Query<T> {
    private final Map<String, Object> equalityRestrictions = new HashMap<>();
    private final Class<T> tClass;
    private Order order;

    public Query(Class<T> tClass) {
        this.tClass = tClass;
    }

    public Query<T> addEqualityRestriction(String propertyName, Object propertyValue) {
        equalityRestrictions.put(propertyName, propertyValue);
        return this;
    }

    public Query<T> setOrder(Order order) {
        this.order = order;
        return this;
    }
}
