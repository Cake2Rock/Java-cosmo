package org.example.spacecats.services;

import org.example.spacecats.entities.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ItemService {
    private final List<Product> inventory = new ArrayList<>();

    public ItemService() {
        Product item1 = new Product();
        item1.setItemId(UUID.randomUUID());
        item1.setItemName("Star Explorer");
        item1.setItemDescription("Navigate through the cosmos effortlessly.");
        item1.setItemCost(199.99);

        Product item2 = new Product();
        item2.setItemId(UUID.randomUUID());
        item2.setItemName("Cosmic Ray Detector");
        item2.setItemDescription("Measure cosmic phenomena with precision.");
        item2.setItemCost(299.99);

        inventory.add(item1);
        inventory.add(item2);
    }

    public List<Product> getAllItems() {
        return inventory;
    }

    public Optional<Product> findItemById(UUID id) {
        return inventory.stream().filter(item -> item.getItemId().equals(id)).findFirst();
    }

    public Product addItem(Product product) {
        product.setItemId(UUID.randomUUID());
        inventory.add(product);
        return product;
    }

    public boolean removeItem(UUID id) {
        return inventory.removeIf(item -> item.getItemId().equals(id));
    }
}
