package com.jfs.training.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jfs.training.entity.FoodItemEntity;
/**
To-Do Item 1.1:
	Define a JPA repository for fetching to provide built-in CRUD operations without requiring manual implementation.
*/
public interface FoodItemDAO extends JpaRepository<FoodItemEntity, Long>{

}