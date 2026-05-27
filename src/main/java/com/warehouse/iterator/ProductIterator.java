package com.warehouse.iterator;

import com.warehouse.entity.Product;

import java.util.List;
import java.util.NoSuchElementException;

public class ProductIterator implements WarehouseIterator<Product> {

    private final List<Product> products;
    private int index;

    public ProductIterator(List<Product> products) {
        this.products = products;
        this.index = 0;
    }

    @Override
    public boolean hasNext() {
        return index < products.size();
    }

    @Override
    public Product next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more products in iterator");
        }
        return products.get(index++);
    }
}
