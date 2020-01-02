package ru.otus.cachehw;

import java.lang.ref.WeakReference;
import java.util.*;

/**
 * @author sergey
 * created on 14.12.18.
 */
public class MyCache<K, V> implements HwCache<K, V> {
    private static final String PUT_ACTION = "PUT";
    private static final String REMOVE_ACTION = "REMOVE";

    private final List<WeakReference<HwListener<K, V>>> listeners = new ArrayList<>();
    private final Map<K, V> cache = new WeakHashMap<>();

    @Override
    public void put(K key, V value) {
        executeListeners(key, value, PUT_ACTION);
        cache.put(key, value);
    }

    @Override
    public void remove(K key) {
        executeListeners(key, cache.get(key), REMOVE_ACTION);
        cache.remove(key);
    }

    @Override
    public V get(K key) {
        return cache.get(key);
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listeners.add(new WeakReference<>(listener));
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listeners.removeIf(wr -> Objects.equals(wr.get(), listener));
    }

    private void executeListeners(K key, V value, String action) {
//        listeners.stream()
//                .filter(wr -> Objects.nonNull(wr.get()))
//                .forEach(wr -> wr.get().notify(key, value, action));
        listeners.forEach(wr -> {
            HwListener<K, V> listener = wr.get();
            if (Objects.nonNull(listener)) {
                listener.notify(key, value, action);
            }
        });
    }

}
