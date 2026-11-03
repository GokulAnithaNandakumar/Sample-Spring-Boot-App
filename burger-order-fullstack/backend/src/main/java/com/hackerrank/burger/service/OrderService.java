package com.hackerrank.burger.service;
import com.hackerrank.burger.model.OrderBean;
import java.util.List;
public interface OrderService {
  OrderBean addOrder(OrderBean order);
  List<OrderBean> getAllOrders();
  OrderBean getOrderById(Long id);
  void deleteOrder(Long id);
}
