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
