package com.hackerrank.burger.repository;

import com.hackerrank.burger.model.OrderBean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/** Wrapper around the Spring Data repository. */
@Component
public class OrderDaoWrapper {

    private final OrderDao dao;

    public OrderDaoWrapper(OrderDao dao) {
        this.dao = dao;
    }

    public OrderBean save(OrderBean order) {
        return dao.save(order);
    }

    public List<OrderBean> findAll() {
        return dao.findAll();
    }

    public Optional<OrderBean> findById(Long id) {
        return dao.findById(id);
    }

    public boolean existsById(Long id) {
        return dao.existsById(id);
    }

    public void deleteById(Long id) {
        dao.deleteById(id);
    }
}
