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

    // Requirement 3:
    // TODOs:
    // - Retrieve the order details for the provided order ID.
    // - Build the order context using order ID, burger name, quantity, total cost, delivery address, order status, and order time.
    // - Get the status message prompt from PromptProvider.
    // - Pass the status message prompt and order context to OrderAiClient to generate the AI-based status message.
    // - Create and return an AiResponse containing the order ID and the generated status message.
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

    // Requirement 4:
    // TODOs:
    // - Retrieve the order details for the provided order ID.
    // - Build the order context using order ID, burger name, quantity, total cost, delivery address, order status, and cancellation eligibility.
    // - Get the cancellation advice prompt from PromptProvider.
    // - Pass the cancellation advice prompt and order context to OrderAiClient to generate the AI-based cancellation advice.
    // - Create and return an AiResponse containing the order ID and the generated cancellation advice.
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

    // Requirement 5:
    // TODOs:
    // - Retrieve the order from the repository using the provided order ID.
    // - If the order exists, return the corresponding OrderEntity.
    // - If not found, throw OrderNotFoundException with an appropriate message.
    private OrderEntity getOrder(Integer orderId) {
        return orderRepository.findById(orderId)
            .orElseThrow(() -> new com.hackerrank.burger.exception.OrderNotFoundException(
                "Order not found with ID: " + orderId
            ));
    }
}
