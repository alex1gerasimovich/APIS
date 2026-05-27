package com.warehouse.singleton;

import com.warehouse.entity.Product;
import com.warehouse.exception.ProductNotFoundException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;

public class WarehouseTest {

    private Warehouse warehouse;

    @BeforeMethod
    public void setUp() {
        warehouse = Warehouse.getInstance();
        warehouse.clearAll();
    }

    @Test
    public void testSingletonReturnsTheSameInstance() {
        Warehouse w1 = Warehouse.getInstance();
        Warehouse w2 = Warehouse.getInstance();
        assertSame(w1, w2);
    }

    @Test
    public void testAddProduct_IncreasesSize() {
        warehouse.addProduct(new Product(1, "Apple", 100, 1.5, "Food"));
        assertEquals(warehouse.getProducts().size(), 1);
    }

    @Test
    public void testAddProduct_StoresCorrectProduct() {
        Product product = new Product(1, "Apple", 100, 1.5, "Food");
        warehouse.addProduct(product);
        assertEquals(warehouse.getProducts().get(0), product);
    }

    @Test
    public void testRemoveProduct_DecreasesSize() throws ProductNotFoundException {
        warehouse.addProduct(new Product(1, "Apple", 100, 1.5, "Food"));
        warehouse.removeProduct(1);
        assertTrue(warehouse.getProducts().isEmpty());
    }

    @Test
    public void testRemoveProduct_ThrowsWhenNotFound() {
        Assert.assertThrows(ProductNotFoundException.class, () -> warehouse.removeProduct(999));
    }

    @Test
    public void testGetProducts_ReturnsDefensiveCopy() {
        warehouse.addProduct(new Product(1, "Apple", 100, 1.5, "Food"));
        List<Product> copy = warehouse.getProducts();
        copy.clear();
        assertEquals(warehouse.getProducts().size(), 1);
    }

    @Test
    public void testClearAll_RemovesAllProducts() {
        warehouse.addProduct(new Product(1, "Apple", 100, 1.5, "Food"));
        warehouse.addProduct(new Product(2, "Laptop", 10, 999.99, "Electronics"));
        warehouse.clearAll();
        assertTrue(warehouse.getProducts().isEmpty());
    }
}
