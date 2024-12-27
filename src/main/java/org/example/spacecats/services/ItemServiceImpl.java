package org.example.spacecats.services;

import org.example.spacecats.domain.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ItemServiceImpl implements ItemService {
    private final List<Product> inventory = new ArrayList<>();

    public ItemServiceImpl() {
        Product item1 = new Product();
        item1.setId(UUID.randomUUID());
        item1.setName("Star Explorer");
        item1.setDescription("Navigate through the cosmos effortlessly.");
        item1.setPrice(199.99);

        Product item2 = new Product();
        item2.setId(UUID.randomUUID());
        item2.setName("Cosmic Ray Detector");
        item2.setDescription("Measure cosmic phenomena with precision.");
        item2.setPrice(299.99);

        inventory.add(item1);
        inventory.add(item2);
    }

    @Override
    public List<Product> getAllItems() {
        return inventory;
    }

    @Override
    public Optional<Product> findItemById(UUID id) {
        return inventory.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst();
    }

    @Override
    public Product addItem(Product product) {
        product.setId(UUID.randomUUID());
        inventory.add(product);
        return product;
    }

    @Override
    public boolean removeItem(UUID id) {
        return inventory.removeIf(item -> item.getId().equals(id));
    }
}
