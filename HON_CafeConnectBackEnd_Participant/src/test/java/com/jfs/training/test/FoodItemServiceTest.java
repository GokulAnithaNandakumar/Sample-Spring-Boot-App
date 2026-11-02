package com.jfs.training.test;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.jfs.training.Application;
import com.jfs.training.bean.FoodItemBean;
import com.jfs.training.services.FoodItemServiceImpl;

/*
 * Following annotation is used to tell that Spring is used to run the tests.
 */
@ExtendWith(SpringExtension.class)

/*
 * Loads the complete Spring Boot application context.
 * Context will be cached and reused for all test cases.
 */
@SpringBootTest(classes = Application.class)

/*
 * Each test case runs inside a transaction.
 * By default, the transaction will be rolled back after test execution.
 */
@Transactional
public class FoodItemServiceTest {

    /* Autowired Service Under Test (SUT) */
    @Autowired
    private FoodItemServiceImpl foodItemServiceImpl;

    /**
     * To-Do Test Case 1:
     * Verify that a new food item can be added successfully.
     *
     * TODO:
     * -- Create a FoodItemBean object
     * -- Populate all mandatory fields
     * -- Invoke addFoodItem() method
     * -- Assert that no exception is thrown
     */
    @Test
    public void testAddFoodItem() {

        // TODO: Create and populate FoodItemBean

        FoodItemBean foodItem=new FoodItemBean();
        foodItem.setName("Burger");
        foodItem.setAvailable(true);
        foodItem.setCategory("Main Course");
        foodItem.setPrice(50.00);


        // TODO: Call addFoodItem() and assert no exception

        Assertions.assertDoesNotThrow(()->{
            foodItemServiceImpl.addFoodItem(foodItem);}
            );
        }
    

    /**
     * To-Do Test Case 2:
     * Verify that fetching all food items returns a non-null list.
     *
     * TODO:
     * -- Invoke getAllFoodItems() method
     * -- Assert that the returned list is not null
     */
    @Test
    public void testGetAllFoodItems() throws Exception {

        // TODO: Call getAllFoodItems()
        List<FoodItemBean> foodItems=foodItemServiceImpl.getAllFoodItems();

        // TODO: Assert the result is not null
        Assertions.assertNotNull(foodItems);

    }

    /**
     * To-Do Test Case 3:
     * Verify add and fetch flow together.
     *
     * TODO:
     * -- Add a new food item
     * -- Fetch all food items
     * -- Assert that the newly added food item exists in the list
     */
    @Test
    public void testAddAndFetchFoodItems() throws Exception {

        // TODO: Create and add FoodItemBean
        FoodItemBean foodItem=new FoodItemBean();
        foodItem.setName("pizza");
        foodItem.setAvailable(true);
        foodItem.setCategory("Main Course");
        foodItem.setPrice(100.0);
        foodItemServiceImpl.addFoodItem(foodItem);

        // TODO: Fetch all food items
        List<FoodItemBean> foodItems=foodItemServiceImpl.getAllFoodItems();
      // TODO: Assert added item is present in the list
      Assertions.assertNotNull(foodItems);
      boolean found=foodItems.stream().anyMatch(item->"pizza".equals(item.getName()));
      Assertions.assertTrue(found , "Newly added food item should exists in the List");
      
    }
}