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
