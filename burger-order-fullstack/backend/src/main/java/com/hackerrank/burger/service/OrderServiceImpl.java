package com.hackerrank.burger.service;

import com.hackerrank.burger.model.OrderBean;
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
        double unitPrice;
        String name = order.getBurgerName();
        if ("Veg-Burger".equals(name)) {
            unitPrice = 100;
        } else if ("Chicken-Burger".equals(name)) {
            unitPrice = 200;
        } else if ("Veg-CrunchBurger".equals(name)) {
            unitPrice = 150;
        } else if ("Chicken-CrunchBurger".equals(name)) {
            unitPrice = 250;
        } else {
            throw new com.hackerrank.burger.exception.BurgerNameMismatchException("Invalid burger name: " + name);
        }
        order.setId(null);
        order.setTotalCost(unitPrice * order.getQuantity());
        order.setOrderStatus("PLACED");
        order.setOrderTime(java.time.LocalDateTime.now());
        return dao.save(order);
    }

    public List<OrderBean> getAllOrders() {
        return dao.findAll();
    }

    public OrderBean getOrderById(Long id) {
        return dao.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
    }

    public OrderBean updateOrder(Long id, OrderBean order) {
        OrderBean existing = getOrderById(id);
        if (order.getBurgerName() != null) existing.setBurgerName(order.getBurgerName());
        if (order.getQuantity() != null) existing.setQuantity(order.getQuantity());
        if (order.getDeliveryAddress() != null) existing.setDeliveryAddress(order.getDeliveryAddress());
        // recalculate total cost
        double unitPrice = 0;
        String name = existing.getBurgerName();
        if ("Veg-Burger".equals(name)) unitPrice = 100;
        else if ("Chicken-Burger".equals(name)) unitPrice = 200;
        else if ("Veg-CrunchBurger".equals(name)) unitPrice = 150;
        else if ("Chicken-CrunchBurger".equals(name)) unitPrice = 250;
        else throw new com.hackerrank.burger.exception.BurgerNameMismatchException("Invalid burger name: " + name);
        existing.setTotalCost(unitPrice * existing.getQuantity());
        existing.setId(id);
        return dao.save(existing);
    }

    public OrderBean patchOrder(Long id, OrderBean order) {
        OrderBean existing = getOrderById(id);
        if (order.getBurgerName() != null) existing.setBurgerName(order.getBurgerName());
        if (order.getQuantity() != null) existing.setQuantity(order.getQuantity());
        if (order.getDeliveryAddress() != null) existing.setDeliveryAddress(order.getDeliveryAddress());
        if (order.getBurgerName() != null || order.getQuantity() != null) {
            double unitPrice = 0;
            String name = existing.getBurgerName();
            if ("Veg-Burger".equals(name)) unitPrice = 100;
            else if ("Chicken-Burger".equals(name)) unitPrice = 200;
            else if ("Veg-CrunchBurger".equals(name)) unitPrice = 150;
            else if ("Chicken-CrunchBurger".equals(name)) unitPrice = 250;
            else throw new com.hackerrank.burger.exception.BurgerNameMismatchException("Invalid burger name: " + name);
            existing.setTotalCost(unitPrice * existing.getQuantity());
        }
        existing.setId(id);
        return dao.save(existing);
    }

    public void deleteOrder(Long id) {
        if (!dao.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
        }
        dao.deleteById(id);
    }
}
