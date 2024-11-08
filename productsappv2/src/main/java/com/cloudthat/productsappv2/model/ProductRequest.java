
package com.cloudthat.productsappv2.model;

import com.cloudthat.productsappv2.entity.Category;
import jakarta.validation.constraints.NotBlank;

public class ProductRequest {
    @NotBlank
    private String productName;
    @NotBlank
    private double price;
    @NotBlank
    private Category category;

    public @NotBlank String getProductName() {
        return productName;
    }

    public void setProductName(@NotBlank String productName) {
        this.productName = productName;
    }

    @NotBlank
    public double getPrice() {
        return price;
    }

    public void setPrice(@NotBlank double price) {
        this.price = price;
    }

    public @NotBlank Category getCategory() {
        return category;
    }

    public void setCategory(@NotBlank Category category) {
        this.category = category;
    }

    public ProductRequest() {
    }

    public ProductRequest(String productName, double price, Category category) {
        this.productName = productName;
        this.price = price;
        this.category = category;
    }
}
