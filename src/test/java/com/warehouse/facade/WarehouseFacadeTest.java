package com.warehouse.facade;

import com.warehouse.entity.Product;
import com.warehouse.exception.InvalidProductDataException;
import com.warehouse.exception.ProductNotFoundException;
import com.warehouse.exception.WarehouseException;
import com.warehouse.iterator.WarehouseIterator;
import com.warehouse.singleton.Warehouse;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;

public class WarehouseFacadeTest {

    private WarehouseFacade facade;

    @BeforeMethod
    public void setUp() {
        Warehouse warehouse = Warehouse.getInstance();
        warehouse.clearAll();
        facade = new WarehouseFacade(warehouse);
    }

    @Test
    public void testAddProduct_ValidProduct() throws WarehouseException {
        facade.addProduct(new Product(1, "Apple", 100, 1.5, "Food"));
        assertEquals(facade.getAllProducts().size(), 1);
    }

    @Test
    public void testAddProduct_NullProduct_ThrowsException() {
        Assert.assertThrows(InvalidProductDataException.class, () -> facade.addProduct(null));
    }

    @Test
    public void testAddProduct_InvalidId_ThrowsException() {
        Assert.assertThrows(InvalidProductDataException.class,
                () -> facade.addProduct(new Product(0, "Apple", 100, 1.5, "Food")));
    }

    @Test
    public void testAddProduct_EmptyName_ThrowsException() {
        Assert.assertThrows(InvalidProductDataException.class,
                () -> facade.addProduct(new Product(1, "", 100, 1.5, "Food")));
    }

    @Test
    public void testAddProduct_NegativePrice_ThrowsException() {
        Assert.assertThrows(InvalidProductDataException.class,
                () -> facade.addProduct(new Product(1, "Apple", 100, -1.0, "Food")));
    }

    @Test
    public void testRemoveProduct_ExistingProduct() throws WarehouseException {
        facade.addProduct(new Product(1, "Apple", 100, 1.5, "Food"));
        facade.removeProduct(1);
        assertTrue(facade.getAllProducts().isEmpty());
    }

    @Test
    public void testRemoveProduct_NotFound_ThrowsException() {
        Assert.assertThrows(ProductNotFoundException.class, () -> facade.removeProduct(999));
    }

    @Test
    public void testGetProductIterator_IteratesAllProducts() throws WarehouseException {
        facade.addProduct(new Product(1, "Apple", 100, 1.5, "Food"));
        facade.addProduct(new Product(2, "Laptop", 10, 999.99, "Electronics"));
        WarehouseIterator<Product> iterator = facade.getProductIterator();
        int count = 0;
        while (iterator.hasNext()) {
            assertNotNull(iterator.next());
            count++;
        }
        assertEquals(count, 2);
    }

    @Test
    public void testGetAllProducts_ReturnsAllAddedProducts() throws WarehouseException {
        facade.addProduct(new Product(1, "Apple", 100, 1.5, "Food"));
        facade.addProduct(new Product(2, "Laptop", 10, 999.99, "Electronics"));
        List<Product> products = facade.getAllProducts();
        assertEquals(products.size(), 2);
    }
}
