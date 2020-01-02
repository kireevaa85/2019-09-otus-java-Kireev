package ru.otus.cachehw;

import java.util.*;

/**
 * @author sergey
 * created on 14.12.18.
 */
public class MyCache<K, V> implements HwCache<K, V> {
    private static final String PUT_ACTION = "PUT";
    private static final String REMOVE_ACTION = "REMOVE";

    private final List<HwListener<K, V>> listeners = new ArrayList<>();
    private final Map<K, V> cache = new WeakHashMap<>();

    @Override
    public void put(K key, V value) {
        listeners.forEach(listener -> listener.notify(key, value, PUT_ACTION));
        cache.put(key, value);
    }

    @Override
    public void remove(K key) {
        listeners.forEach(listener -> listener.notify(key, cache.get(key), REMOVE_ACTION));
        cache.remove(key);
    }

    @Override
    public V get(K key) {
        return cache.get(key);
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listeners.remove(listener);
    }
}
