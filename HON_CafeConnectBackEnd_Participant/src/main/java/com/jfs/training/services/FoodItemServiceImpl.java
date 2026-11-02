package com.jfs.training.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jfs.training.bean.FoodItemBean;
import com.jfs.training.dao.FoodItemDAOWrapper;

/*
 * This class acts as the business layer between controllers and DAO layer. It
 * delegates the actual data operations to FoodItemDAOWrapper.

 */
@Service
public class FoodItemServiceImpl implements FoodItemService {

	/* DAO wrapper to perform CRUD operations on food items */
	@Autowired
	private FoodItemDAOWrapper foodDAOWrapper;
	
	 /**
		To-Do Item 1.4: This method should add food Items.
		TODO:
			--Invoke the addFoodItem method of foodDAOWrapper
	     */

	@Override
	public void addFoodItem(FoodItemBean foodItem) throws Exception {
		foodDAOWrapper.addFoodItem(foodItem);
		
	}
	/**
   	To-Do Item 1.5: This method should fetch all food item details.
   	TODO:
   		--Invoke the getAllFoodItems method of foodDAOWrapper.
	 	--Return the food Item List.
      */
	@Override
	public List<FoodItemBean> getAllFoodItems() throws Exception {
		return foodDAOWrapper.getAllFoodItems();
	}
}