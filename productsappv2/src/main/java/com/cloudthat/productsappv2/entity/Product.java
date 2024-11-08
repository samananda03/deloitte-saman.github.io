
package com.cloudthat.productsappv2.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String productName;
    @Min(value = 1)
    private double price;
    @Enumerated(EnumType.ORDINAL)
    private Category category;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank String getProductName() {
        return productName;
    }

    public void setProductName(@NotBlank String productName) {
        this.productName = productName;
    }

    @Min(value = 1)
    public double getPrice() {
        return price;
    }

    public void setPrice(@Min(value = 1) double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Product() {
    }

    public Product(String productName, double price, Category category) {
        this.productName = productName;
        this.price = price;
        this.category = category;
    }
}
