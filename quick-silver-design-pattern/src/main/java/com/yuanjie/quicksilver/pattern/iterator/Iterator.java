package com.yuanjie.quicksilver.pattern.iterator;

import java.util.Optional;

public interface Iterator<E> {

    boolean hasNext();

    Optional<E> next();

    Optional<E> current();

    void handleFirstIterateEvent();
}
