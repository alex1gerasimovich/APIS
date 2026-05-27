package com.warehouse.reader;

import com.warehouse.entity.Product;
import com.warehouse.exception.WarehouseException;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.testng.Assert.*;

public class ProductReaderTest {

    private Path tempDir;

    @BeforeMethod
    public void createTempDir() throws IOException {
        tempDir = Files.createTempDirectory("warehouse-test-");
    }

    @AfterMethod
    public void deleteTempDir() throws IOException {
        if (tempDir != null && Files.exists(tempDir)) {
            Files.walk(tempDir)
                    .sorted(Comparator.reverseOrder())
                    .forEach(path -> {
                        try {
                            Files.delete(path);
                        } catch (IOException ignored) {
                        }
                    });
        }
    }

    @Test
    public void testReadProducts_ValidLines_ReturnsAllProducts() throws IOException, WarehouseException {
        Path file = tempDir.resolve("products.txt");
        Files.write(file, Arrays.asList(
                "1;Apple;100;1.5;Food",
                "2;Laptop;10;999.99;Electronics"
        ));
        List<Product> products = new ProductReader().readProducts(file.toString());
        assertEquals(products.size(), 2);
    }

    @Test
    public void testReadProducts_SkipsInvalidLines() throws IOException, WarehouseException {
        Path file = tempDir.resolve("products.txt");
        Files.write(file, Arrays.asList(
                "1;Apple;100;1.5;Food",
                "invalid line",
                "abc;Bad;10;5.0;Cat",
                "2;Laptop;10;999.99;Electronics"
        ));
        List<Product> products = new ProductReader().readProducts(file.toString());
        assertEquals(products.size(), 2);
    }

    @Test
    public void testReadProducts_ParsedProductFieldsAreCorrect() throws IOException, WarehouseException {
        Path file = tempDir.resolve("products.txt");
        Files.write(file, Arrays.asList("7;Notebook;200;2.99;Stationery"));
        List<Product> products = new ProductReader().readProducts(file.toString());
        Product product = products.get(0);
        assertEquals(product.getId(), 7);
        assertEquals(product.getName(), "Notebook");
        assertEquals(product.getQuantity(), 200);
        assertEquals(product.getPrice(), 2.99, 0.001);
        assertEquals(product.getCategory(), "Stationery");
    }

    @Test
    public void testReadProducts_AllInvalidLines_ReturnsEmptyList() throws IOException, WarehouseException {
        Path file = tempDir.resolve("products.txt");
        Files.write(file, Arrays.asList("bad1", "bad2", "bad3"));
        List<Product> products = new ProductReader().readProducts(file.toString());
        assertTrue(products.isEmpty());
    }

    @Test
    public void testReadProducts_EmptyFile_ReturnsEmptyList() throws IOException, WarehouseException {
        Path file = tempDir.resolve("products.txt");
        Files.write(file, new byte[0]);
        List<Product> products = new ProductReader().readProducts(file.toString());
        assertTrue(products.isEmpty());
    }

    @Test
    public void testReadProducts_FileNotFound_ThrowsWarehouseException() {
        Assert.assertThrows(WarehouseException.class,
                () -> new ProductReader().readProducts("/non/existent/path/products.txt"));
    }

    @Test
    public void testReadProducts_SkipsLinesWithNegativeQuantity() throws IOException, WarehouseException {
        Path file = tempDir.resolve("products.txt");
        Files.write(file, Arrays.asList(
                "1;Apple;100;1.5;Food",
                "2;Coffee;-10;8.99;Beverages"
        ));
        List<Product> products = new ProductReader().readProducts(file.toString());
        assertEquals(products.size(), 1);
        assertEquals(products.get(0).getName(), "Apple");
    }
}
