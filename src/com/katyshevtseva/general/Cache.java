package com.katyshevtseva.general;

import java.util.HashMap;
import java.util.Map;

/**
 * @param <Key> этот класс должен переопределять hashCode, equals
 */
public class Cache<Key, Value> {
    private final Map<Key, Value> map = new HashMap<>();
    private final OneArgOneAnswerKnob<Key, Value> valueSupplier;

    public Cache(OneArgOneAnswerKnob<Key, Value> valueSupplier) {
        this.valueSupplier = valueSupplier;
    }

    public Value getCachedValue(Key key) {
        Value value = map.get(key);
        if (value == null) {
            value = valueSupplier.execute(key);
            map.put(key, value);
        }
        return value;
    }
}
