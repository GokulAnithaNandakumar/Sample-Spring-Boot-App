package com.jfs.training.entity;

import javax.persistence.*;

/*
 * Entity class representing the "food_items" table in the database.
 * This class is mapped to a database table using JPA annotations and is used by
 * Spring Data JPA for CRUD operations.
 */

@Entity
@Table(name = "food_items")
public class FoodItemEntity {

	/* Primary key of the food item (auto-generated) */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/* Name of the food item */
	private String name;

	/* Category of the food item (e.g., starter, main course, dessert) */
	private String category;

	/* Price of the food item */
	private Double price;

	/* Availability status of the food item */
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
