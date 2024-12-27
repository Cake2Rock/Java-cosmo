package org.example.spacecats.domain;

import lombok.Data;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

@Data
public class Product {

    private UUID id;

    @NotBlank(message = "Name cannot be blank")
    @Size(max = 100, message = "Name should not exceed 100 characters")
    private String name;

    @Size(max = 255, message = "Description should not exceed 255 characters")
    private String description;

    @Min(value = 0, message = "Price must be non-negative")
    private Double price;
}
