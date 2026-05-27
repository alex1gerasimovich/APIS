package com.warehouse.reader;

import com.warehouse.entity.Product;
import com.warehouse.exception.WarehouseException;
import com.warehouse.validator.ProductValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProductReader {

    private static final Logger LOGGER = LogManager.getLogger(ProductReader.class);
    private final ProductValidator validator;

    public ProductReader() {
        this.validator = new ProductValidator();
    }

    public List<Product> readProducts(String filePath) throws WarehouseException {
        Path path = Paths.get(filePath);
        try (Stream<String> lines = Files.lines(path)) {
            return lines
                    .filter(line -> {
                        boolean valid = validator.isValidLine(line);
                        if (!valid) {
                            LOGGER.warn("Skipping invalid line: [{}]", line);
                        }
                        return valid;
                    })
                    .map(this::parseLine)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new WarehouseException("Failed to read products from file: " + filePath, e);
        }
    }

    private Product parseLine(String line) {
        String[] parts = line.split(";");
        int id = Integer.parseInt(parts[0].trim());
        String name = parts[1].trim();
        int quantity = Integer.parseInt(parts[2].trim());
        double price = Double.parseDouble(parts[3].trim());
        String category = parts[4].trim();
        return new Product(id, name, quantity, price, category);
    }
}
