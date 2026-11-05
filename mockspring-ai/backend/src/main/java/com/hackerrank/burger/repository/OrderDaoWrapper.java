package com.hackerrank.burger.repository;

import com.hackerrank.burger.beans.OrderBean;
import com.hackerrank.burger.beans.OrderEntity;
import com.hackerrank.burger.exception.BurgerNameMismatchException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderDaoWrapper {

    private final OrderDao orderRepository;

    public OrderDaoWrapper(OrderDao orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderBean save(OrderBean order) {
        OrderEntity entity = convertBeanToEntity(order);
        return convertEntityToBean(orderRepository.save(entity));
    }

    public List<OrderBean> findAll() {
        List<OrderBean> orders = new ArrayList<>();
        for (OrderEntity entity : orderRepository.findAll()) {
            orders.add(convertEntityToBean(entity));
        }
        return orders;
    }

    public OrderBean findById(Integer id) {
        return orderRepository.findById(id).map(OrderDaoWrapper::convertEntityToBean)
            .orElseThrow(() -> new com.hackerrank.burger.exception.OrderNotFoundException("Order not found: " + id));
    }

    public boolean existsById(Integer id) {
        return orderRepository.existsById(id);
    }

    public void deleteById(Integer id) {
        orderRepository.deleteById(id);
    }

    // Requirement 1:
    // TODOs:
    // - Implement the addOrder method.
    // - Convert OrderBean to OrderEntity.
    // - Calculate the total cost based on burger name, quantity, and the following prices:
    //   Veg-Burger = 100, Chicken-Burger = 200, Veg-CrunchBurger = 150, Chicken-CrunchBurger = 250
    // - Throw BurgerNameMismatchException for an invalid burger name.
    // - Set the calculated total cost and save the order.
    // - Convert the saved OrderEntity back to OrderBean.
    // - Return the updated OrderBean.
    public OrderBean addOrder(OrderBean burgerBean) {
        OrderEntity entity = convertBeanToEntity(burgerBean);
        double price;
        switch (burgerBean.getBurgerName()) {
            case "Veg-Burger":
                price = 100;
                break;
            case "Chicken-Burger":
                price = 200;
                break;
            case "Veg-CrunchBurger":
                price = 150;
                break;
            case "Chicken-CrunchBurger":
                price = 250;
                break;
            default:
                throw new BurgerNameMismatchException("Invalid burger name");
        }
        entity.setTotalCost(price * burgerBean.getQuantity());
        entity.setOrderStatus("PLACED");
        entity.setOrderTime(java.time.LocalDateTime.now());
        OrderEntity savedOrder = orderRepository.save(entity);
        return convertEntityToBean(savedOrder);
    }

    // Requirement 2:
    // TODOs:
    // - Implement the getOrdersList method.
    // - Fetch all orders from the repository.
    // - Convert each OrderEntity into OrderBean.
    // - Add the converted OrderBean objects to a list.
    // - Return the list of orders.
    public List<OrderBean> getOrdersList() {
        List<OrderBean> orders = new ArrayList<>();
        for (OrderEntity entity : orderRepository.findAll()) {
            orders.add(convertEntityToBean(entity));
        }
        return orders;
    }

    public OrderBean getOrder(Integer id) {
        return orderRepository.findById(id)
            .map(OrderDaoWrapper::convertEntityToBean)
            .orElseThrow(() -> new com.hackerrank.burger.exception.OrderNotFoundException("Order not found: " + id));
    }

    public static OrderEntity convertBeanToEntity(OrderBean bean) {
        OrderEntity entity = new OrderEntity();
        BeanUtils.copyProperties(bean, entity);
        return entity;
    }

    public static OrderBean convertEntityToBean(OrderEntity entity) {
        OrderBean bean = new OrderBean();
        BeanUtils.copyProperties(entity, bean);
        return bean;
    }
}
