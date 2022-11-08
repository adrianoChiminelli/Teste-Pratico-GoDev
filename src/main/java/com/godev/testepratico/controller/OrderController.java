package com.godev.testepratico.controller;

import com.godev.testepratico.model.OrderEntity;
import com.godev.testepratico.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

<<<<<<< HEAD
=======
import javax.validation.Valid;
>>>>>>> Nova-Implementacao
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
<<<<<<< HEAD
    public OrderEntity saveOrder(@RequestBody OrderEntity order){
        if(order.getTotalValue() == null){
            order.setTotalValue(0.);
        }
        if(order.getPercentualDiscount() == null){
            order.setPercentualDiscount(0.);
        }
=======
    public OrderEntity saveOrder(@RequestBody @Valid OrderEntity order){
>>>>>>> Nova-Implementacao
        return orderService.saveOrder(order);
    }

    @GetMapping("/{id}")
    public OrderEntity getOrderById (@PathVariable UUID id) throws Exception {
<<<<<<< HEAD
        return orderService.getOrderById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não foi encontrado!!!"));
    }

    @PutMapping("/{id}")
    public OrderEntity updateOrder(@PathVariable UUID id, @RequestBody OrderEntity newOrder){
        Optional<OrderEntity> orderOpt = orderService.getOrderById(id);
        if (orderOpt.isPresent()){
            OrderEntity order = orderOpt.get();

            order.setNumber(newOrder.getNumber());
            order.setDate(newOrder.getDate());
            order.setPercentualDiscount(newOrder.getPercentualDiscount());
            order.setTotalValue(newOrder.getTotalValue());

            return orderService.saveOrder(order);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não foi encontrado!!!");
        }
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable UUID id){
        if (orderService.getOrderById(id).isPresent()){
            orderService.deleteOrderById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não foi encontrado!!!");
        }
=======
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
>>>>>>> Nova-Implementacao
    }

    @GetMapping
    public List<OrderEntity> getAllOrders(){
        return orderService.getOrders();
    }

}
