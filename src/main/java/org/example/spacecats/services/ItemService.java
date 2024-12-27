package org.example.spacecats.services;

import org.example.spacecats.domain.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ItemService {
    List<Product> getAllItems();
    Optional<Product> findItemById(UUID id);
    Product addItem(Product product);
    boolean removeItem(UUID id);
}
