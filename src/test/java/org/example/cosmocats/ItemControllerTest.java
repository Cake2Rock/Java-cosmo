package org.example.spacecats.controllers;

import org.example.spacecats.controllers.ItemController;
import org.example.spacecats.domain.Product;
import org.example.spacecats.dto.ItemResponse;
import org.example.spacecats.mappers.ItemMapper;
import org.example.spacecats.services.ItemService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ItemController.class)
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemService itemService;

    @MockBean
    private ItemMapper itemMapper;

    @Test
    void testFetchAllItems_emptyList() throws Exception {
        Mockito.when(itemService.getAllItems()).thenReturn(Collections.emptyList());
        Mockito.when(itemMapper.toResponseList(Collections.emptyList())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/items"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void testFetchItemById_found() throws Exception {
        UUID testId = UUID.randomUUID();
        Product mockProduct = new Product();
        mockProduct.setId(testId);
        mockProduct.setName("Test Product");
        mockProduct.setDescription("Desc");
        mockProduct.setPrice(99.99);

        ItemResponse mockResponse = new ItemResponse();
        mockResponse.setId(testId);
        mockResponse.setName("Test Product");
        mockResponse.setDescription("Desc");
        mockResponse.setPrice(99.99);

        Mockito.when(itemService.findItemById(testId)).thenReturn(Optional.of(mockProduct));
        Mockito.when(itemMapper.toResponse(mockProduct)).thenReturn(mockResponse);

        mockMvc.perform(get("/api/v1/items/" + testId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testId.toString()))
                .andExpect(jsonPath("$.name").value("Test Product"));
    }

    @Test
    void testFetchItemById_notFound() throws Exception {
        UUID testId = UUID.randomUUID();

        Mockito.when(itemService.findItemById(testId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/items/" + testId))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteItem_success() throws Exception {
        UUID testId = UUID.randomUUID();
        Mockito.when(itemService.removeItem(testId)).thenReturn(true);

        mockMvc.perform(delete("/api/v1/items/" + testId))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteItem_notFound() throws Exception {
        UUID testId = UUID.randomUUID();
        Mockito.when(itemService.removeItem(testId)).thenReturn(false);

        mockMvc.perform(delete("/api/v1/items/" + testId))
                .andExpect(status().isNotFound());
    }

@Test
void testCreateItem_success() throws Exception {
    String newItemJson = """
        {
            "name": "New Product",
            "description": "Something cosmic",
            "price": 123.45
        }
        """;

    UUID createdId = UUID.randomUUID();
    Product savedProduct = new Product();
    savedProduct.setId(createdId);
    savedProduct.setName("New Product");
    savedProduct.setDescription("Something cosmic");
    savedProduct.setPrice(123.45);

    ItemResponse savedResponse = new ItemResponse();
    savedResponse.setId(createdId);
    savedResponse.setName("New Product");
    savedResponse.setDescription("Something cosmic");
    savedResponse.setPrice(123.45);

    Mockito.when(itemMapper.toDomain(any())).thenReturn(savedProduct);
    Mockito.when(itemService.addItem(savedProduct)).thenReturn(savedProduct);
    Mockito.when(itemMapper.toResponse(savedProduct)).thenReturn(savedResponse);

    mockMvc.perform(post("/api/v1/items")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(newItemJson))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(createdId.toString()))
            .andExpect(jsonPath("$.name").value("New Product"));
}

}
