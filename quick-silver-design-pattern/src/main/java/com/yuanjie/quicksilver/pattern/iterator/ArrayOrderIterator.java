package com.yuanjie.quicksilver.pattern.iterator;

import java.util.Optional;

public class ArrayOrderIterator<E> implements Iterator<E> {

    private ArrayLineTable<E> list;

    private int cursor;

    public ArrayOrderIterator(ArrayLineTable<E> list) {
        this.list = list;
        this.cursor = 0;
    }

    @Override
    public void handleFirstIterateEvent() {
        return;
    }

    @Override
    public boolean hasNext() {
        return Optional.ofNullable(list).map(LineTable::size).map(size -> cursor < size).orElse(false);
    }

    @Override
    public Optional<E> next() {
        return hasNext() ? list.get(cursor++) : Optional.empty();
    }

    @Override
    public Optional<E> current() {
        return Optional.ofNullable(list).flatMap(lineTable -> lineTable.get(cursor));
    }
}
