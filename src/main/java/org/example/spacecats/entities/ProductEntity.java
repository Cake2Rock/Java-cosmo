package org.example.spacecats.entities;

import java.util.UUID;

public class ProductEntity {
    private UUID uniqueId;
    private String productName;
    private String productDetails;
    private double cost;

    public ProductEntity(UUID uniqueId, String productName, String productDetails, double cost) {
        this.uniqueId = uniqueId;
        this.productName = productName;
        this.productDetails = productDetails;
        this.cost = cost;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(UUID uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(String productDetails) {
        this.productDetails = productDetails;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
