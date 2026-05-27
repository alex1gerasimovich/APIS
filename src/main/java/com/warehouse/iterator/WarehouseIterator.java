package com.warehouse.iterator;

public interface WarehouseIterator<T> {
    boolean hasNext();
    T next();
}
