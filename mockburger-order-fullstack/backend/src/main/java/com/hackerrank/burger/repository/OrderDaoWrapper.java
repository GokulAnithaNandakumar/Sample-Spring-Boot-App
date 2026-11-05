package com.hackerrank.burger.repository;

import com.hackerrank.burger.beans.OrderEntity;
import com.hackerrank.burger.model.OrderBean;
import com.hackerrank.burger.exception.BurgerNameMismatchException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/** Wrapper around the Spring Data repository. */
@Component
public class OrderDaoWrapper {

    private final OrderDao dao;

    public OrderDaoWrapper(OrderDao dao) {
        this.dao = dao;
    }

    public OrderBean save(OrderBean order) {
        OrderEntity entity = convertBeanToEntity(order);
        return convertEntityToBean(dao.save(entity));
    }

    public List<OrderBean> findAll() {
        List<OrderBean> orders = new ArrayList<>();
        for (OrderEntity entity : dao.findAll()) {
            orders.add(convertEntityToBean(entity));
        }
        return orders;
    }

    public OrderBean findById(Long id) {
        return dao.findById(id).map(OrderDaoWrapper::convertEntityToBean)
            .orElseThrow(() -> new com.hackerrank.burger.exception.OrderNotFoundException("Order not found: " + id));
    }

    public boolean existsById(Long id) {
        return dao.existsById(id);
    }

    public void deleteById(Long id) {
        dao.deleteById(id);
    }

    public OrderBean addOrder(OrderBean burgerBean) {
        OrderEntity entity = convertBeanToEntity(burgerBean);
        double unitPrice;
        String name = burgerBean.getBurgerName();
        if ("Veg-Burger".equals(name)) {
            unitPrice = 100;
        } else if ("Chicken-Burger".equals(name)) {
            unitPrice = 200;
        } else if ("Veg-CrunchBurger".equals(name)) {
            unitPrice = 150;
        } else if ("Chicken-CrunchBurger".equals(name)) {
            unitPrice = 250;
        } else {
            throw new BurgerNameMismatchException("Invalid burger name: " + name);
        }
        entity.setTotalCost(unitPrice * burgerBean.getQuantity());
        entity.setOrderStatus("PLACED");
        entity.setOrderTime(java.time.LocalDateTime.now());
        entity.setCreatedAt(java.time.LocalDateTime.now());
        OrderEntity saved = dao.save(entity);
        return convertEntityToBean(saved);
    }

    public List<OrderBean> getOrdersList() {
        List<OrderBean> orders = new ArrayList<>();
        for (OrderEntity entity : dao.findAll()) {
            orders.add(convertEntityToBean(entity));
        }
        return orders;
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
