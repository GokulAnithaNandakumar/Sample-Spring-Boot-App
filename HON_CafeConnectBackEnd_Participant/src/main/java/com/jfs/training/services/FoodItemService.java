package com.jfs.training.services;

import java.util.List;

import com.jfs.training.bean.FoodItemBean;

/* Defines the business operations that can be performed on FoodItemBean objects */
public interface FoodItemService {
	void addFoodItem(FoodItemBean foodItem) throws Exception;

	List<FoodItemBean> getAllFoodItems() throws Exception;
}
