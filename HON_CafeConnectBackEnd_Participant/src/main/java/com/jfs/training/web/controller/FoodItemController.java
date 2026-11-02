package com.jfs.training.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jfs.training.bean.FoodItemBean;
import com.jfs.training.services.FoodItemServiceImpl;

/*
 * REST Controller class for managing food item related web requests.
 * Handles operations like:
 * - providing an empty bean structure (optional /add)
 * - saving food items (/save)
 * - listing all food items (/list)
 * - centralized exception handling
 *
 * Note: Uses Spring Boot 2.7.3 compatible javax.validation annotations.
 */
@RestController
@RequestMapping("/food")
@CrossOrigin(origins = "*") // Allow cross-origin requests from any domain
public class FoodItemController {

    @Autowired
    private FoodItemServiceImpl foodItemServiceImpl;

    /* Optional: Provides an empty FoodItemBean (useful for client-side form initialization) */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ResponseEntity<FoodItemBean> showAddForm() {
        return ResponseEntity.ok(new FoodItemBean());
    }

    /**
     * To-Do Item 1.6: This method should save a new food item submitted from the client.
     *
     * TODO:
     * --Map the URL to /save.
     * --Retrieve the foodItem submitted by the client.
     * --Perform validation checks.
     * --If validation fails, return BAD_REQUEST along with validation error details.
     * --On successful form validation, invoke the addFoodItem method of foodItemServiceImpl.
     * --Return CREATED status with a success message after successful save.
     *
     * (This aligns with the assignment intent described for saveFoodItem()).
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<?> saveFoodItem(@Valid @ModelAttribute FoodItemBean foodItem, BindingResult result)
            throws Exception {
if(result.hasErrors()){
    return ResponseEntity.badRequest().body(result.getAllErrors());
}
foodItemServiceImpl.addFoodItem(foodItem);

        return ResponseEntity.status(HttpStatus.CREATED).body("Food item have successfully saved");
         // Participant has to complete
    }

    /**
     * To-Do Item 1.7: This method should fetch all food items from Database.
     *
     * TODO:
     * --Map the URL to /list
     * --Invoke the getAllFoodItems method of foodItemServiceImpl
     * --Return OK status with the food item list as response body.
     *
     * (This aligns with the assignment intent describe
     * d for listing food items).
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity<List<FoodItemBean>> listFoodItems() throws Exception {
    List<FoodItemBean> foodItems=foodItemServiceImpl.getAllFoodItems();
        return ResponseEntity.ok(foodItems); // Participant has to complete
    }

    /* Handles all uncaught exceptions */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleAllExceptions(Exception exception) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exception.getMessage());
    }
}