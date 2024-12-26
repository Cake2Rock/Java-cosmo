package org.example.spacecats.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ItemResponse {
    private UUID id;
    private String name;
    private String description;
    private Double price;
}
