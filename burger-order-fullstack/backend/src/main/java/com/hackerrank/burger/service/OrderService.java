package com.hackerrank.burger.service;
import com.hackerrank.burger.model.OrderBean;
import java.util.List;
public interface OrderService {
  OrderBean addOrder(OrderBean order);
  List<OrderBean> getAllOrders();
  OrderBean getOrderById(Long id);
  OrderBean updateOrder(Long id, OrderBean order);
  OrderBean patchOrder(Long id, OrderBean order);
  void deleteOrder(Long id);
}
