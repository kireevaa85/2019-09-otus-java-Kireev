package ru.otus.hw02DIYarrayList;

import java.util.*;
import java.util.stream.Collectors;

public class DIYarrayList<T> implements List<T> {
    private static final int INIT_CAPACITY = 10;
    private static final double MULTIPLY_ARRAY_SIZE = 1.5;

    private Object[] array;
    private int size;

    public DIYarrayList() {
        array = new Object[INIT_CAPACITY];
    }

    public DIYarrayList(int initCapacity) {
        if (initCapacity > 0) {
            array = new Object[initCapacity];
        } else if (initCapacity == 0) {
            array = new Object[]{};
        } else {
            throw new IllegalArgumentException("initalCapacity can't be negative.");
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public String toString() {
        return Arrays.stream(array)
                .limit(size)
                .map(o -> o == null ? "null" : o.toString())
                .collect(Collectors.joining(",", "{", "}"));
    }

    @Override
    @SuppressWarnings("unchecked")
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return (T) array[index];
    }

    @Override
    @SuppressWarnings("unchecked")
    public T set(int index, T element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        T prevValue = (T) array[index];
        array[index] = element;
        return prevValue;
    }

    @Override
    public boolean add(T t) {
        if (size == array.length) {
            array = Arrays.copyOf(array, (int) (size * MULTIPLY_ARRAY_SIZE));
        }
        array[size] = t;
        size++;
        return true;
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(array, size);
    }

    @Override
    public ListIterator<T> listIterator() {
        return new DIYarrayListIterator();
    }

    private class DIYarrayListIterator implements ListIterator<T> {

        private int cursor = 0;
        private int lastReturnIndex = -1;

        @Override
        public boolean hasNext() {
            return cursor < size;
        }

        @Override
        public T next() {
            if (cursor >= size) {
                throw new NoSuchElementException();
            }
            return get(lastReturnIndex = cursor++);
        }

        @Override
        public boolean hasPrevious() {
            return cursor > 0;
        }

        @Override
        public T previous() {
            if (cursor - 1 < 0) {
                throw new NoSuchElementException();
            }
            return get(lastReturnIndex = --cursor);
        }

        @Override
        public int nextIndex() {
            return cursor;
        }

        @Override
        public int previousIndex() {
            return cursor - 1;
        }

        @Override
        public void set(T t) {
            DIYarrayList.this.set(lastReturnIndex, t);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(T t) {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int index, T element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

}
