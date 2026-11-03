package com.hackerrank.burger.beans;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class OrderBean {

    private Integer orderId;

    @NotBlank(message = "Burger name is a required field")
    private String burgerName;

    @NotNull(message = "Quantity should not be null")
    @Min(value = 1, message = "Quantity should be at least 1")
    @Max(value = 10, message = "Quantity should be at most 10")
    private Integer quantity;

    private Double totalCost;

    @NotBlank(message = "Delivery Address is a required field")
    private String deliveryAddress;

    private String orderStatus;

    private java.time.LocalDateTime orderTime;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getBurgerName() {
        return burgerName;
    }

    public void setBurgerName(String burgerName) {
        this.burgerName = burgerName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public java.time.LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(java.time.LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }
}
