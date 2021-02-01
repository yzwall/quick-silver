package com.yuanjie.quicksilver.pattern.iterator;

import java.util.Optional;

public class ArrayReverseOrderIterator<E> implements Iterator<E> {

    private ArrayLineTable<E> list;

    private int cursor;

    public ArrayReverseOrderIterator(ArrayLineTable<E> list) {
        this.list = list;
        this.cursor = list.size() - 1;
    }

    @Override
    public void handleFirstIterateEvent() {
        cursor = list.size() - 1;
    }

    @Override
    public boolean hasNext() {
        return cursor >= 0;
    }

    @Override
    public Optional<E> next() {
        return hasNext() ? list.get(cursor--) : Optional.empty();
    }

    @Override
    public Optional<E> current() {
        return Optional.ofNullable(list).flatMap(lineTable -> lineTable.get(cursor));
    }
}
