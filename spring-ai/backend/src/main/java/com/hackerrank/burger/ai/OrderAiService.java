package com.hackerrank.burger.ai;

import com.hackerrank.burger.beans.OrderEntity;
import com.hackerrank.burger.repository.OrderDao;
import org.springframework.stereotype.Service;

@Service
public class OrderAiService {

    private final OrderDao orderRepository;
    private final OrderAiClient orderAiClient;
    private final OrderPromptProvider promptProvider;

    public OrderAiService(OrderDao orderRepository, OrderAiClient orderAiClient, OrderPromptProvider promptProvider) {
        this.orderRepository = orderRepository;
        this.orderAiClient = orderAiClient;
        this.promptProvider = promptProvider;
    }

    public AiResponse generateStatusMessage(Integer orderId) {
        OrderEntity order = getOrder(orderId);
        String orderContext = String.format(
            "Order ID: %d, Burger Name: %s, Quantity: %d, Total Cost: %s, Delivery Address: %s, Order Status: %s, Order Time: %s",
            order.getOrderId(),
            order.getBurgerName(),
            order.getQuantity(),
            order.getTotalCost(),
            order.getDeliveryAddress(),
            order.getOrderStatus(),
            order.getOrderTime()
        );
        String prompt = promptProvider.statusMessagePrompt();
        String message = orderAiClient.generate(prompt, orderContext);

        AiResponse response = new AiResponse();
        response.setOrderId(orderId);
        response.setMessage(message);
        return response;
    }

    public AiResponse generateCancellationAdvice(Integer orderId) {
        OrderEntity order = getOrder(orderId);
        boolean cancellationEligible =
            com.hackerrank.burger.beans.OrderStatus.PLACED.name().equalsIgnoreCase(order.getOrderStatus());
        String orderContext = String.format(
            "Order ID: %d, Burger Name: %s, Quantity: %d, Total Cost: %s, Delivery Address: %s, Order Status: %s, Cancellation Eligible: %s",
            order.getOrderId(),
            order.getBurgerName(),
            order.getQuantity(),
            order.getTotalCost(),
            order.getDeliveryAddress(),
            order.getOrderStatus(),
            cancellationEligible
        );
        String prompt = promptProvider.cancellationAdvicePrompt();
        String advice = orderAiClient.generate(prompt, orderContext);

        AiResponse response = new AiResponse();
        response.setOrderId(orderId);
        response.setMessage(advice);
        return response;
    }

    private OrderEntity getOrder(Integer orderId) {
        return orderRepository.findById(orderId)
            .orElseThrow(() -> new com.hackerrank.burger.exception.OrderNotFoundException(
                "Order not found with ID: " + orderId
            ));
    }
}
