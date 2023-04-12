package com.godev.testepratico.controller;

import com.godev.testepratico.model.OrderEntity;
import com.godev.testepratico.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderEntity saveOrder(@RequestBody @Valid OrderEntity order){
        return orderService.saveOrder(order);
    }

    @GetMapping("/{id}")
    public OrderEntity getOrderById (@PathVariable UUID id) throws Exception {
        return orderService.getOrderById(id);
    }

    @PutMapping("/{id}")
    public OrderEntity updateOrder(@PathVariable UUID id, @RequestBody @Valid OrderEntity newOrder){
        return orderService.updateOrder(id, newOrder);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void deleteOrder(@PathVariable UUID id){
        orderService.deleteOrderById(id);
    }

    @GetMapping
    public List<OrderEntity> getAllOrders(){
        return orderService.getOrders();
    }

}
