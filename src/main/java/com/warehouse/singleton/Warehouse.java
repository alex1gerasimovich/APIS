package com.warehouse.singleton;

import com.warehouse.entity.Product;
import com.warehouse.exception.ProductNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class Warehouse {

    private static volatile Warehouse instance;
    private final List<Product> products;

    private Warehouse() {
        products = new ArrayList<>();
    }

    public static Warehouse getInstance() {
        if (instance == null) {
            synchronized (Warehouse.class) {
                if (instance == null) {
                    instance = new Warehouse();
                }
            }
        }
        return instance;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(int productId) throws ProductNotFoundException {
        Product toRemove = products.stream()
                .filter(p -> p.getId() == productId)
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException(
                        "Product with id " + productId + " not found"));
        products.remove(toRemove);
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products);
    }

    public void clearAll() {
        products.clear();
    }
}
