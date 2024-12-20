package org.example.spacecats.services;

import org.example.spacecats.entities.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest {

    private ProductService productService;

    @BeforeEach
    void setUp() {
        productService = new ProductService();
    }

    @Test
    void testFetchAllProducts() {
        List<ProductEntity> products = productService.fetchAllProducts();
        assertNotNull(products);
        assertFalse(products.isEmpty(), "Product list should not be empty");
    }

    @Test
    void testFindProductByIdExists() {
        ProductEntity existingProduct = productService.fetchAllProducts().get(0);
        Optional<ProductEntity> foundProduct = productService.findProductById(existingProduct.getUniqueId());

        assertTrue(foundProduct.isPresent(), "Product should be found by ID");
        assertEquals(existingProduct.getUniqueId(), foundProduct.get().getUniqueId(), "Product ID should match");
    }

    @Test
    void testFindProductByIdNotExists() {
        Optional<ProductEntity> foundProduct = productService.findProductById(UUID.randomUUID());

        assertFalse(foundProduct.isPresent(), "No product should be found for a random ID");
    }

    @Test
    void testAddNewProduct() {
        ProductEntity newProduct = new ProductEntity(UUID.randomUUID(), "Cosmic Rover", "Explore distant planets", 500.00);
        ProductEntity addedProduct = productService.addNewProduct(newProduct);

        assertNotNull(addedProduct, "Added product should not be null");
        assertEquals(newProduct.getName(), addedProduct.getName(), "Product name should match");
        assertEquals(newProduct.getPrice(), addedProduct.getPrice(), "Product price should match");

        List<ProductEntity> products = productService.fetchAllProducts();
        assertTrue(products.contains(addedProduct), "Added product should be in the product list");
    }

    @Test
    void testDeleteProductExists() {
        ProductEntity existingProduct = productService.fetchAllProducts().get(0);
        boolean isDeleted = productService.deleteProduct(existingProduct.getUniqueId());

        assertTrue(isDeleted, "Product should be successfully deleted");

        Optional<ProductEntity> foundProduct = productService.findProductById(existingProduct.getUniqueId());
        assertFalse(foundProduct.isPresent(), "Deleted product should not be found");
    }

    @Test
    void testDeleteProductNotExists() {
        boolean isDeleted = productService.deleteProduct(UUID.randomUUID());

        assertFalse(isDeleted, "Deleting a non-existent product should return false");
    }
}
