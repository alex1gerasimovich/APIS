package com.warehouse.command;

import com.warehouse.entity.Product;
import com.warehouse.singleton.Warehouse;

public class AddProductCommand implements Command {

    private final Warehouse warehouse;
    private final Product product;

    public AddProductCommand(Warehouse warehouse, Product product) {
        this.warehouse = warehouse;
        this.product = product;
    }

    @Override
    public void execute() {
        warehouse.addProduct(product);
    }
}
