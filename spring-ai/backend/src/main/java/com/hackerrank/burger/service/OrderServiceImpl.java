package com.hackerrank.burger.service;

import com.hackerrank.burger.beans.OrderBean;
import com.hackerrank.burger.repository.OrderDaoWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderDaoWrapper dao;

    public OrderServiceImpl(OrderDaoWrapper dao) {
        this.dao = dao;
    }

    @Override
    public OrderBean addOrder(OrderBean order) {
        if (order.getBurgerName() == null || order.getBurgerName().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Burger name is required");
        }
        if (order.getQuantity() == null || order.getQuantity() < 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantity must be at least 1");
        }
        if (order.getDeliveryAddress() == null || order.getDeliveryAddress().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Delivery address is required");
        }
        order.setOrderId(null);
        return dao.addOrder(order);
    }

    @Override
    public List<OrderBean> getAllOrders() {
        return dao.getOrdersList();
    }

    @Override
    public OrderBean getOrderById(Integer id) {
        return dao.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
    }

    @Override
    public void deleteOrder(Integer id) {
        if (!dao.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
        }
        dao.deleteById(id);
    }
}
