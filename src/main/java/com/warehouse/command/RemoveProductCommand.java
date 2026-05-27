package com.warehouse.command;

import com.warehouse.exception.ProductNotFoundException;
import com.warehouse.singleton.Warehouse;

public class RemoveProductCommand implements Command {

    private final Warehouse warehouse;
    private final int productId;

    public RemoveProductCommand(Warehouse warehouse, int productId) {
        this.warehouse = warehouse;
        this.productId = productId;
    }

    @Override
    public void execute() throws ProductNotFoundException {
        warehouse.removeProduct(productId);
    }
}
