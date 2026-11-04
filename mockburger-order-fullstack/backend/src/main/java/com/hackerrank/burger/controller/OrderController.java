package com.hackerrank.burger.controller;

import com.hackerrank.burger.model.OrderBean;
import com.hackerrank.burger.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<OrderBean> addOrder(@Valid @RequestBody OrderBean order) {
        OrderBean saved = service.addOrder(order);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PostMapping(value = "/addorder", produces = MediaType.TEXT_HTML_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> orderBurger(@RequestBody @Valid OrderBean bean, org.springframework.validation.Errors errors) {
        if (errors.hasErrors()) {
            return new ResponseEntity<>(errors.getAllErrors().toString(), HttpStatus.BAD_REQUEST);
        }
        OrderBean savedOrder = service.addOrder(bean);
        String response = "Order ID: " + savedOrder.getId() + ", Total Cost: " + savedOrder.getTotalCost();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/getAllOrders")
    public ResponseEntity<List<OrderBean>> getAllOrders() {
        return ResponseEntity.ok(service.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderBean> getOrder(@PathVariable Long id) {
        return ResponseEntity.ok(service.getOrderById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderBean> updateOrder(@PathVariable Long id, @Valid @RequestBody OrderBean order) {
        return ResponseEntity.ok(service.updateOrder(id, order));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<OrderBean> patchOrder(@PathVariable Long id, @RequestBody OrderBean order) {
        return ResponseEntity.ok(service.patchOrder(id, order));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        service.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
