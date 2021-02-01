package com.yuanjie.quicksilver.pattern.iterator;

import java.util.Optional;

public interface LineTable<T> {

    Iterator<T> iterator();

    Iterator<T> reverseIterator();

    Optional<T> get(int index);

    int size();

    boolean isEmpty();

    boolean add(T element);

    boolean add(T ...element);

    boolean remove(int index);
}
