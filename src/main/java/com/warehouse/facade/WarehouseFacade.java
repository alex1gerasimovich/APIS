package com.warehouse.facade;

import com.warehouse.command.AddProductCommand;
import com.warehouse.command.Command;
import com.warehouse.command.RemoveProductCommand;
import com.warehouse.entity.Product;
import com.warehouse.exception.WarehouseException;
import com.warehouse.iterator.ProductIterator;
import com.warehouse.iterator.WarehouseIterator;
import com.warehouse.singleton.Warehouse;
import com.warehouse.validator.ProductValidator;

import java.util.List;

public class WarehouseFacade {

    private final Warehouse warehouse;
    private final ProductValidator validator;

    public WarehouseFacade() {
        this(Warehouse.getInstance());
    }

    public WarehouseFacade(Warehouse warehouse) {
        this.warehouse = warehouse;
        this.validator = new ProductValidator();
    }

    public void addProduct(Product product) throws WarehouseException {
        validator.validate(product);
        Command command = new AddProductCommand(warehouse, product);
        command.execute();
    }

    public void removeProduct(int productId) throws WarehouseException {
        Command command = new RemoveProductCommand(warehouse, productId);
        command.execute();
    }

    public WarehouseIterator<Product> getProductIterator() {
        return new ProductIterator(warehouse.getProducts());
    }

    public List<Product> getAllProducts() {
        return warehouse.getProducts();
    }
}
