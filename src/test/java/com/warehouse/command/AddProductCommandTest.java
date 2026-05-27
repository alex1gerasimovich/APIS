package com.warehouse.command;

import com.warehouse.entity.Product;
import com.warehouse.singleton.Warehouse;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class AddProductCommandTest {

    private Warehouse warehouse;

    @BeforeMethod
    public void setUp() {
        warehouse = Warehouse.getInstance();
        warehouse.clearAll();
    }

    @Test
    public void testExecute_AddsProductToWarehouse() throws Exception {
        Product product = new Product(1, "Apple", 100, 1.5, "Food");
        new AddProductCommand(warehouse, product).execute();
        assertEquals(warehouse.getProducts().size(), 1);
        assertEquals(warehouse.getProducts().get(0), product);
    }

    @Test
    public void testExecute_AddsMultipleProducts() throws Exception {
        new AddProductCommand(warehouse, new Product(1, "Apple", 100, 1.5, "Food")).execute();
        new AddProductCommand(warehouse, new Product(2, "Laptop", 10, 999.99, "Electronics")).execute();
        assertEquals(warehouse.getProducts().size(), 2);
    }
}
