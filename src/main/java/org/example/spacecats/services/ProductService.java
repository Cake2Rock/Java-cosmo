package org.example.spacecats.services;

import org.example.spacecats.entities.ProductEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {
    private final List<ProductEntity> inventory = new ArrayList<>();

    public ProductService() {
        ProductEntity item1 = new ProductEntity(UUID.randomUUID(), "Star Explorer", "Explore stars and planets", 150.75);
        ProductEntity item2 = new ProductEntity(UUID.randomUUID(), "Galaxy Telescope", "High resolution galactic viewing", 300.50);
        inventory.add(item1);
        inventory.add(item2);
    }

    public List<ProductEntity> fetchAllProducts() {
        return inventory;
    }

    public Optional<ProductEntity> findProductById(UUID id) {
        return inventory.stream().filter(item -> item.getUniqueId().equals(id)).findFirst();
    }

    public ProductEntity addNewProduct(ProductEntity product) {
        product.setUniqueId(UUID.randomUUID());
        inventory.add(product);
        return product;
    }

    public boolean removeProduct(UUID id) {
        return inventory.removeIf(item -> item.getUniqueId().equals(id));
    }
}
