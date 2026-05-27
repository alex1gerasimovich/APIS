package com.warehouse.command;

import com.warehouse.entity.Product;
import com.warehouse.exception.ProductNotFoundException;
import com.warehouse.singleton.Warehouse;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class RemoveProductCommandTest {

    private Warehouse warehouse;

    @BeforeMethod
    public void setUp() {
        warehouse = Warehouse.getInstance();
        warehouse.clearAll();
    }

    @Test
    public void testExecute_RemovesExistingProduct() throws Exception {
        warehouse.addProduct(new Product(1, "Apple", 100, 1.5, "Food"));
        new RemoveProductCommand(warehouse, 1).execute();
        assertTrue(warehouse.getProducts().isEmpty());
    }

    @Test
    public void testExecute_ThrowsWhenProductNotFound() {
        RemoveProductCommand command = new RemoveProductCommand(warehouse, 999);
        Assert.assertThrows(ProductNotFoundException.class, command::execute);
    }
}
