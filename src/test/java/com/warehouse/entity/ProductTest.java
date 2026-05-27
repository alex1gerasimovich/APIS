package com.warehouse.entity;

import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class ProductTest {

    @Test
    public void testConstructorAndGetters() {
        Product product = new Product(1, "Apple", 100, 1.5, "Food");
        assertEquals(product.getId(), 1);
        assertEquals(product.getName(), "Apple");
        assertEquals(product.getQuantity(), 100);
        assertEquals(product.getPrice(), 1.5);
        assertEquals(product.getCategory(), "Food");
    }

    @Test
    public void testSetters() {
        Product product = new Product(1, "Apple", 100, 1.5, "Food");
        product.setId(2);
        product.setName("Banana");
        product.setQuantity(50);
        product.setPrice(0.99);
        product.setCategory("Fruits");

        assertEquals(product.getId(), 2);
        assertEquals(product.getName(), "Banana");
        assertEquals(product.getQuantity(), 50);
        assertEquals(product.getPrice(), 0.99);
        assertEquals(product.getCategory(), "Fruits");
    }

    @Test
    public void testToString_ContainsAllFields() {
        Product product = new Product(1, "Apple", 100, 1.5, "Food");
        String result = product.toString();
        assertTrue(result.contains("1"));
        assertTrue(result.contains("Apple"));
        assertTrue(result.contains("100"));
        assertTrue(result.contains("1.5"));
        assertTrue(result.contains("Food"));
    }

    @Test
    public void testEquals_SameReference() {
        Product product = new Product(1, "Apple", 100, 1.5, "Food");
        assertEquals(product, product);
    }

    @Test
    public void testEquals_EqualObjects() {
        Product p1 = new Product(1, "Apple", 100, 1.5, "Food");
        Product p2 = new Product(1, "Apple", 100, 1.5, "Food");
        assertEquals(p1, p2);
    }

    @Test
    public void testEquals_DifferentId() {
        Product p1 = new Product(1, "Apple", 100, 1.5, "Food");
        Product p2 = new Product(2, "Apple", 100, 1.5, "Food");
        assertNotEquals(p1, p2);
    }

    @Test
    public void testEquals_DifferentName() {
        Product p1 = new Product(1, "Apple", 100, 1.5, "Food");
        Product p2 = new Product(1, "Banana", 100, 1.5, "Food");
        assertNotEquals(p1, p2);
    }

    @Test
    public void testEquals_DifferentPrice() {
        Product p1 = new Product(1, "Apple", 100, 1.5, "Food");
        Product p2 = new Product(1, "Apple", 100, 2.0, "Food");
        assertNotEquals(p1, p2);
    }

    @Test
    public void testEquals_Null() {
        Product product = new Product(1, "Apple", 100, 1.5, "Food");
        assertNotEquals(product, null);
    }

    @Test
    public void testEquals_DifferentClass() {
        Product product = new Product(1, "Apple", 100, 1.5, "Food");
        assertNotEquals(product, "some string");
    }

    @Test
    public void testHashCode_ConsistencyForEqualObjects() {
        Product p1 = new Product(1, "Apple", 100, 1.5, "Food");
        Product p2 = new Product(1, "Apple", 100, 1.5, "Food");
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    public void testHashCode_DifferentForDifferentObjects() {
        Product p1 = new Product(1, "Apple", 100, 1.5, "Food");
        Product p2 = new Product(2, "Banana", 50, 0.99, "Fruits");
        Assert.assertNotEquals(p1.hashCode(), p2.hashCode());
    }
}
