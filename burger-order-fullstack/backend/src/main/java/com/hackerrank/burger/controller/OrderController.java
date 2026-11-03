package com.hackerrank.burger.controller;

import com.hackerrank.burger.model.OrderBean;
import com.hackerrank.burger.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/burgers")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping("/addOrder")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderBean addOrder(@Valid @RequestBody OrderBean order) {
        return service.addOrder(order);
    }

    @GetMapping("/getAllOrders")
    public List<OrderBean> getAllOrders() {
        return service.getAllOrders();
    }

    @GetMapping("/{id}")
    public OrderBean getOrder(@PathVariable Long id) {
        return service.getOrderById(id);
    }

    @PutMapping("/{id}")
    public OrderBean updateOrder(@PathVariable Long id, @Valid @RequestBody OrderBean order) {
        return service.updateOrder(id, order);
    }

    @PatchMapping("/{id}")
    public OrderBean patchOrder(@PathVariable Long id, @RequestBody OrderBean order) {
        return service.patchOrder(id, order);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable Long id) {
        service.deleteOrder(id);
    }
}
