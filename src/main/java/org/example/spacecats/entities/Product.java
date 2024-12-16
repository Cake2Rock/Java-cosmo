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
