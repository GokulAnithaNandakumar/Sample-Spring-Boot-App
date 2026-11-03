package com.hackerrank.burger.ai;

public class AiResponse {
    private Integer orderId;
    private String message;

    public AiResponse() {
    }

    public AiResponse(Integer orderId, String message) {
        this.orderId = orderId;
        this.message = message;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
