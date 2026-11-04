package com.hackerrank.burger.repository;

import com.hackerrank.burger.model.OrderBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDao extends JpaRepository<OrderBean, Long> {
}
