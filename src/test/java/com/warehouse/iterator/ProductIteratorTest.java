package com.warehouse.iterator;

import com.warehouse.entity.Product;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.NoSuchElementException;

import static org.testng.Assert.*;

public class ProductIteratorTest {

    @Test
    public void testHasNext_EmptyList_ReturnsFalse() {
        ProductIterator iterator = new ProductIterator(Collections.emptyList());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testHasNext_NonEmptyList_ReturnsTrue() {
        ProductIterator iterator = new ProductIterator(
                Collections.singletonList(new Product(1, "Apple", 100, 1.5, "Food")));
        assertTrue(iterator.hasNext());
    }

    @Test
    public void testNext_ReturnsCorrectProduct() {
        Product product = new Product(1, "Apple", 100, 1.5, "Food");
        ProductIterator iterator = new ProductIterator(Arrays.asList(product));
        assertEquals(iterator.next(), product);
    }

    @Test
    public void testHasNext_FalseAfterAllElementsConsumed() {
        ProductIterator iterator = new ProductIterator(
                Collections.singletonList(new Product(1, "Apple", 100, 1.5, "Food")));
        iterator.next();
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testIteratesAllProducts() {
        ProductIterator iterator = new ProductIterator(Arrays.asList(
                new Product(1, "Apple", 100, 1.5, "Food"),
                new Product(2, "Laptop", 10, 999.99, "Electronics"),
                new Product(3, "T-Shirt", 50, 19.99, "Clothing")
        ));
        int count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        assertEquals(count, 3);
    }

    @Test
    public void testIteratesInOrder() {
        Product p1 = new Product(1, "Apple", 100, 1.5, "Food");
        Product p2 = new Product(2, "Laptop", 10, 999.99, "Electronics");
        ProductIterator iterator = new ProductIterator(Arrays.asList(p1, p2));
        assertEquals(iterator.next(), p1);
        assertEquals(iterator.next(), p2);
    }

    @Test
    public void testNext_ThrowsNoSuchElementException_WhenExhausted() {
        ProductIterator iterator = new ProductIterator(
                Collections.singletonList(new Product(1, "Apple", 100, 1.5, "Food")));
        iterator.next();
        Assert.assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    public void testNext_ThrowsNoSuchElementException_OnEmptyList() {
        ProductIterator iterator = new ProductIterator(Collections.emptyList());
        Assert.assertThrows(NoSuchElementException.class, iterator::next);
    }
}
