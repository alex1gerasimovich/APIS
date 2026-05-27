package com.warehouse;

import com.warehouse.entity.Product;
import com.warehouse.exception.WarehouseException;
import com.warehouse.facade.WarehouseFacade;
import com.warehouse.iterator.WarehouseIterator;
import com.warehouse.reader.ProductReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class Main {

    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        ProductReader reader = new ProductReader();
        WarehouseFacade facade = new WarehouseFacade();

        try {
            String filePath = Main.class.getClassLoader()
                    .getResource("data/products.txt")
                    .getPath();
            List<Product> products = reader.readProducts(filePath);

            for (Product product : products) {
                facade.addProduct(product);
            }

            System.out.println("=== All products in warehouse ===");
            WarehouseIterator<Product> iterator = facade.getProductIterator();
            while (iterator.hasNext()) {
                System.out.println(iterator.next());
            }

            System.out.println("\nRemoving product with id = 1...");
            facade.removeProduct(1);

            System.out.println("\n=== Products after removal ===");
            iterator = facade.getProductIterator();
            while (iterator.hasNext()) {
                System.out.println(iterator.next());
            }

        } catch (WarehouseException e) {
            LOGGER.error("Warehouse operation failed: {}", e.getMessage(), e);
        }
    }
}
