package com.hackerrank.burger.tools;

import com.hackerrank.burger.beans.OrderEntity;
import com.hackerrank.burger.repository.OrderDao;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

@Component
public class OrderTools {

    private final OrderDao orderDao;

    public OrderTools(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    // TODO: Expose order lookup as an AI tool.
    // TODO: Validate the order id before querying the repository.
    // TODO: Return a concise, user-friendly order status summary.
    @Tool(description = "Look up a burger order by its id and return its status and total cost")
    public String getOrderStatus(Integer orderId) {
        OrderEntity order = orderDao.findById(orderId).orElse(null);
        if (order == null) {
            return "Order not found: " + orderId;
        }
        return "Order " + order.getOrderId() + " is " + order.getOrderStatus()
            + " with total cost " + order.getTotalCost() + ".";
    }
}
