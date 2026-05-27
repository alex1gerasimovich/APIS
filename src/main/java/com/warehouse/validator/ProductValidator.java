package com.warehouse.validator;

import com.warehouse.entity.Product;
import com.warehouse.exception.InvalidProductDataException;

public class ProductValidator {

    private static final int MIN_ID = 1;
    private static final int MIN_QUANTITY = 0;
    private static final double MIN_PRICE = 0.0;
    private static final int EXPECTED_FIELDS_COUNT = 5;

    public void validate(Product product) throws InvalidProductDataException {
        if (product == null) {
            throw new InvalidProductDataException("Product cannot be null");
        }
        if (product.getId() < MIN_ID) {
            throw new InvalidProductDataException(
                    "Product id must be positive, got: " + product.getId());
        }
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new InvalidProductDataException("Product name cannot be empty");
        }
        if (product.getQuantity() < MIN_QUANTITY) {
            throw new InvalidProductDataException(
                    "Product quantity cannot be negative, got: " + product.getQuantity());
        }
        if (product.getPrice() < MIN_PRICE) {
            throw new InvalidProductDataException(
                    "Product price cannot be negative, got: " + product.getPrice());
        }
        if (product.getCategory() == null || product.getCategory().trim().isEmpty()) {
            throw new InvalidProductDataException("Product category cannot be empty");
        }
    }

    public boolean isValidLine(String line) {
        if (line == null || line.trim().isEmpty()) {
            return false;
        }
        String[] parts = line.split(";");
        if (parts.length != EXPECTED_FIELDS_COUNT) {
            return false;
        }
        try {
            int id = Integer.parseInt(parts[0].trim());
            String name = parts[1].trim();
            int quantity = Integer.parseInt(parts[2].trim());
            double price = Double.parseDouble(parts[3].trim());
            String category = parts[4].trim();
            validate(new Product(id, name, quantity, price, category));
            return true;
        } catch (NumberFormatException | InvalidProductDataException e) {
            return false;
        }
    }
}
