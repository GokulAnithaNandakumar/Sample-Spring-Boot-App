package com.hackerrank.burger.controller;
import com.hackerrank.burger.model.OrderBean;
import com.hackerrank.burger.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:3000")
@RestController @RequestMapping("/api/burgers")
public class OrderController {
  private final OrderService service;
  public OrderController(OrderService service){this.service=service;}
  @PostMapping("/addOrder") @ResponseStatus(HttpStatus.CREATED)
  public OrderBean addOrder(@Valid @RequestBody OrderBean order){return service.addOrder(order);}
  @GetMapping("/getAllOrders") public List<OrderBean> getAllOrders(){return service.getAllOrders();}
  @GetMapping("/{id}") public OrderBean getOrder(@PathVariable Long id){return service.getOrderById(id);}
  @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void deleteOrder(@PathVariable Long id){service.deleteOrder(id);}
}
