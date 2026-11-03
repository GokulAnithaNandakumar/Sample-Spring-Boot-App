package com.hackerrank.burger.service;

import com.hackerrank.burger.beans.OrderBean;

import java.util.List;

public interface OrderService {
    OrderBean addOrder(OrderBean order);

    List<OrderBean> getAllOrders();

    OrderBean getOrderById(Integer id);

    void deleteOrder(Integer id);
}
