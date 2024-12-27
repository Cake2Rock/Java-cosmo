package org.example.spacecats.controllers;

import jakarta.validation.Valid;
import org.example.spacecats.domain.Product;
import org.example.spacecats.dto.CreateItemRequest;
import org.example.spacecats.dto.ItemResponse;
import org.example.spacecats.mappers.ItemMapper;
import org.example.spacecats.services.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/items")
public class ItemController {

    private final ItemService itemService;
    private final ItemMapper itemMapper;

    public ItemController(ItemService itemService, ItemMapper itemMapper) {
        this.itemService = itemService;
        this.itemMapper = itemMapper;
    }

    @GetMapping
    public ResponseEntity<List<ItemResponse>> fetchAllItems() {
        List<Product> products = itemService.getAllItems();
        List<ItemResponse> response = itemMapper.toResponseList(products);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemResponse> fetchItemById(@PathVariable UUID id) {
        return itemService.findItemById(id)
                .map(product -> ResponseEntity.ok(itemMapper.toResponse(product)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ItemResponse> createItem(
            @Valid @RequestBody CreateItemRequest dto
    ) {
        Product product = itemMapper.toDomain(dto);
        Product created = itemService.addItem(product);
        ItemResponse response = itemMapper.toResponse(created);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeItem(@PathVariable UUID id) {
        boolean removed = itemService.removeItem(id);
        return removed
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
