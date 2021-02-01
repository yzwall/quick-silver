package com.yuanjie.quicksilver.pattern.iterator;

import com.google.common.collect.Lists;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

@Data
public class ArrayLineTable<T> implements LineTable<T> {

    private Iterator<T> orderIterator;

    private Iterator<T> reverseOrderIterator;

    private ArrayList<T> list;

    private ArrayLineTable() {
        list = Lists.newArrayList();
        orderIterator = new ArrayOrderIterator(this);
        reverseOrderIterator = new ArrayReverseOrderIterator(this);
    }

    public static ArrayLineTable of() {
        ArrayLineTable arrayLineTable = new ArrayLineTable();
        arrayLineTable.setList(Lists.newArrayList());
        arrayLineTable.setOrderIterator(new ArrayOrderIterator(arrayLineTable));
        arrayLineTable.setReverseOrderIterator(new ArrayReverseOrderIterator(arrayLineTable));
        return arrayLineTable;
    }

    @Override
    public Iterator<T> iterator() {
        return orderIterator;
    }

    @Override
    public Iterator<T> reverseIterator() {
        return reverseOrderIterator;
    }

    @Override
    public boolean isEmpty() {
        return CollectionUtils.isEmpty(list);
    }

    @Override
    public Optional<T> get(int index) {
        if (index >= list.size()) {
            throw new NoSuchElementException();
        }
        return Optional.ofNullable(list.get(index));
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean add(T element) {
        return list.add(element);
    }

    @Override
    public boolean add(T... element) {
        return list.addAll(Lists.newArrayList(element));
    }

    @Override
    public boolean remove(int index) {
        list.remove(index);
        return true;
    }
}
