package com.jfs.training.bean;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Represents a food item in the Spring Boot application.
 * This bean is typically used as a request/response DTO
 * and includes Bean Validation rules for input validation.
 */
public class FoodItemBean {

    /* Unique identifier of the food item */
    private Long id;

    /* Name of the food item. Must not be empty */
    @NotEmpty(message = "Food name is required")
    private String name;

    /* Category of the food item (starter, main course, dessert, etc.) */
    @NotEmpty(message = "Category is required")
    private String category;

    /* Price of the food item. Must be greater than zero */
    @NotNull(message = "Price is required")
    @DecimalMin(value = "1.0", message = "Price must be greater than 0")
    private Double price;

    /* Indicates whether the food item is available */
    @AssertTrue(message = "Availability must be true")
    private Boolean available;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}