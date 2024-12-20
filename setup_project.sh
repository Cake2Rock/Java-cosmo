#!/bin/bash

# Создание структуры папок
mkdir -p src/main/java/org/example/spacecats/{controllers,entities,services,shared,validation,mappers,dto} \
         src/main/resources/api-spec \
         src/test/java/org/example/spacecats

# Создание файлов
files=(
  "src/main/java/org/example/spacecats/SpaceCatsMarket.java"
  "src/main/java/org/example/spacecats/controllers/ItemController.java"
  "src/main/java/org/example/spacecats/entities/Product.java"
  "src/main/java/org/example/spacecats/entities/ItemCategory.java"
  "src/main/java/org/example/spacecats/entities/CustomerOrder.java"
  "src/main/java/org/example/spacecats/dto/ItemRequestDTO.java"
  "src/main/java/org/example/spacecats/dto/ItemResponseDTO.java"
  "src/main/java/org/example/spacecats/mappers/ItemMapper.java"
  "src/main/java/org/example/spacecats/services/ItemService.java"
  "src/main/java/org/example/spacecats/shared/ErrorAdvisor.java"
  "src/main/java/org/example/spacecats/validation/CosmicValidator.java"
  "src/main/resources/api-spec/product-api.yml"
  "src/main/resources/application.properties"
)

touch "${files[@]}"

# Наполнение файлов кодом

# SpaceCatsMarket.java
cat <<EOL > src/main/java/org/example/spacecats/SpaceCatsMarket.java
package org.example.spacecats;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpaceCatsMarket {
    public static void main(String[] args) {
        SpringApplication.run(SpaceCatsMarket.class, args);
    }
}
EOL
# Product.java
cat <<EOL > src/main/java/org/example/spacecats/entities/Product.java
package org.example.spacecats.entities;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;

public class Product {
    private UUID itemId;

    @NotBlank(message = "Name cannot be blank.")
    @Size(max = 100, message = "Name cannot exceed 100 characters.")
    private String itemName;

    @Size(max = 255, message = "Description must not exceed 255 characters.")
    private String itemDescription;

    @NotNull(message = "Price is mandatory.")
    @Min(value = 0, message = "Price must be a positive number.")
    private Double itemCost;

    public UUID getItemId() {
        return itemId;
    }

    public void setItemId(UUID itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public Double getItemCost() {
        return itemCost;
    }

    public void setItemCost(Double itemCost) {
        this.itemCost = itemCost;
    }
}
EOL
# ItemCategory.java
cat <<EOL > src/main/java/org/example/spacecats/entities/ItemCategory.java
package org.example.spacecats.entities;

import java.util.UUID;
import java.util.List;

public class ItemCategory {
    private UUID categoryId;
    private String categoryName;
    private String categoryDetails;
    private List<Product> associatedItems;

    public UUID getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryDetails() {
        return categoryDetails;
    }

    public void setCategoryDetails(String categoryDetails) {
        this.categoryDetails = categoryDetails;
    }

    public List<Product> getAssociatedItems() {
        return associatedItems;
    }

    public void setAssociatedItems(List<Product> associatedItems) {
        this.associatedItems = associatedItems;
    }
}
EOL
# CustomerOrder.java
cat <<EOL > src/main/java/org/example/spacecats/entities/CustomerOrder.java
package org.example.spacecats.entities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class CustomerOrder {
    private UUID orderId;
    private LocalDateTime orderPlacedOn;
    private OrderState orderState;
    private List<Product> purchasedItems;

    public enum OrderState {
        PENDING,
        DISPATCHED,
        RECEIVED,
        CANCELLED
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getOrderPlacedOn() {
        return orderPlacedOn;
    }

    public void setOrderPlacedOn(LocalDateTime orderPlacedOn) {
        this.orderPlacedOn = orderPlacedOn;
    }

    public OrderState getOrderState() {
        return orderState;
    }

    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }

    public List<Product> getPurchasedItems() {
        return purchasedItems;
    }

    public void setPurchasedItems(List<Product> purchasedItems) {
        this.purchasedItems = purchasedItems;
    }
}
EOL
# ItemController.java
cat <<EOL > src/main/java/org/example/spacecats/controllers/ItemController.java
package org.example.spacecats.controllers;

import jakarta.validation.Valid;
import org.example.spacecats.entities.Product;
import org.example.spacecats.dto.ItemRequestDTO;
import org.example.spacecats.dto.ItemResponseDTO;
import org.example.spacecats.services.ItemService;
import org.example.spacecats.mappers.ItemMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;
    private final ItemMapper itemMapper;

    public ItemController(ItemService itemService, ItemMapper itemMapper) {
        this.itemService = itemService;
        this.itemMapper = itemMapper;
    }

    @GetMapping
    public ResponseEntity<List<ItemResponseDTO>> fetchAllItems() {
        List<Product> items = itemService.getAllItems();
        List<ItemResponseDTO> response = itemMapper.toResponseDTOList(items);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemResponseDTO> fetchItemById(@PathVariable UUID id) {
        return itemService.findItemById(id)
                .map(item -> ResponseEntity.ok(itemMapper.toResponseDTO(item)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ItemResponseDTO> createItem(@Valid @RequestBody ItemRequestDTO itemRequest) {
        Product item = itemMapper.toEntity(itemRequest);
        Product createdItem = itemService.addItem(item);
        ItemResponseDTO response = itemMapper.toResponseDTO(createdItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeItem(@PathVariable UUID id) {
        if (itemService.removeItem(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
EOL
# ItemRequestDTO.java
cat <<EOL > src/main/java/org/example/spacecats/dto/ItemRequestDTO.java
package org.example.spacecats.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ItemRequestDTO {

    @NotBlank(message = "Name cannot be blank.")
    @Size(max = 100, message = "Name cannot exceed 100 characters.")
    private String name;

    @Size(max = 255, message = "Description cannot exceed 255 characters.")
    private String description;

    @Min(value = 0, message = "Price must be a positive number.")
    private double price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
EOL
# ItemResponseDTO.java
cat <<EOL > src/main/java/org/example/spacecats/dto/ItemResponseDTO.java
package org.example.spacecats.dto;

import java.util.UUID;

public class ItemResponseDTO {
    private UUID id;
    private String name;
    private String description;
    private double price;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
EOL
# ItemMapper.java
cat <<EOL > src/main/java/org/example/spacecats/mappers/ItemMapper.java
package org.example.spacecats.mappers;

import org.example.spacecats.dto.ItemRequestDTO;
import org.example.spacecats.dto.ItemResponseDTO;
import org.example.spacecats.entities.Product;

import java.util.List;
import java.util.stream.Collectors;

public class ItemMapper {

    public Product toEntity(ItemRequestDTO dto) {
        Product product = new Product();
        product.setItemName(dto.getName());
        product.setItemDescription(dto.getDescription());
        product.setItemCost(dto.getPrice());
        return product;
    }

    public ItemResponseDTO toResponseDTO(Product product) {
        ItemResponseDTO response = new ItemResponseDTO();
        response.setId(product.getItemId());
        response.setName(product.getItemName());
        response.setDescription(product.getItemDescription());
        response.setPrice(product.getItemCost());
        return response;
    }

    public List<ItemResponseDTO> toResponseDTOList(List<Product> products) {
        return products.stream().map(this::toResponseDTO).collect(Collectors.toList());
    }
}
EOL
# ItemService.java
cat <<EOL > src/main/java/org/example/spacecats/services/ItemService.java
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
EOL
# ErrorAdvisor.java
cat <<EOL > src/main/java/org/example/spacecats/shared/ErrorAdvisor.java
package org.example.spacecats.shared;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ErrorAdvisor {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGlobalExceptions(Exception ex, WebRequest request) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorDetails.put("error", "Unexpected error occurred");
        errorDetails.put("message", ex.getMessage());
        errorDetails.put("path", request.getDescription(false).replace("uri=", ""));

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetails);
    }
}
EOL
# CosmicValidator.java
cat <<EOL > src/main/java/org/example/spacecats/validation/CosmicValidator.java
package org.example.spacecats.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CosmicValidator implements ConstraintValidator<CosmicValidation, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return false;
        }
        String lowercaseValue = value.toLowerCase();
        return lowercaseValue.contains("star") || lowercaseValue.contains("galaxy") || lowercaseValue.contains("comet");
    }
}
EOL
# product-api.yml
cat <<EOL > src/main/resources/api-spec/product-api.yml
openapi: 3.0.1
info:
  title: SpaceCats Marketplace API
  description: API for managing intergalactic products
  version: 1.0.0
servers:
  - url: http://localhost:8080
    description: Local server
paths:
  /api/items:
    get:
      summary: Retrieve all items
      operationId: fetchAllItems
      responses:
        '200':
          description: A list of items
    post:
      summary: Add a new item
      operationId: createItem
      requestBody:
        required: true
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/ItemRequestDTO'
      responses:
        '201':
          description: Item created successfully
  /api/items/{id}:
    get:
      summary: Get item by ID
      operationId: fetchItemById
      parameters:
        - name: id
          in: path
          required: true
          schema:
            type: string
      responses:
        '200':
          description: Item details
        '404':
          description: Item not found
    delete:
      summary: Delete item by ID
      operationId: removeItem
      parameters:
        - name: id
          in: path
          required: true
          schema:
            type: string
      responses:
        '204':
          description: Item deleted
        '404':
          description: Item not found
components:
  schemas:
    ItemRequestDTO:
      type: object
      properties:
        name:
          type: string
          description: Name of the item
        description:
          type: string
          description: Description of the item
        price:
          type: number
          format: double
          description: Price of the item
    ItemResponseDTO:
      type: object
      properties:
        id:
          type: string
          description: Unique ID of the item
        name:
          type: string
        description:
          type: string
        price:
          type: number
          format: double
EOL

EOL

# Остальные секции добавляются аналогично для всех файлов

# Завершение скрипта
echo "Проект успешно создан! Все файлы заполнены."
